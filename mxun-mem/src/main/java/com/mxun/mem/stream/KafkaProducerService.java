package com.mxun.mem.stream;

import com.alibaba.fastjson2.JSON;
import com.mxun.common.enums.KafkaMessageStatusEnum;
import com.mxun.mem.entity.KafkaMessage;
import com.mxun.mem.service.KafkaMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KafkaProducerService {


    @Autowired
    private ThreadPoolTaskExecutor appExecutor;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaMessageService kafkaMessageService;


    /**
     * @Description: 发送kafka消息
     * @Author: liuzhilin
     * @Date: 2025/4/6 10:28
     */
    public void sendMessage(String topic, Object messageKeyObj, Object objectMessage) {
        String messageKey = String.valueOf(messageKeyObj);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, messageKey, JSON.toJSONString(objectMessage));
        future.exceptionally(ex -> {
            log.error("Failed to send Kafka message to topic: {}, key: {}, error: {}", topic, messageKey, ex.getMessage());
            // 发送失败处理
            kafkaMessageService.saveFailedMessage(topic, messageKey, objectMessage);
            return null;
        });
    }

    /**
     * @Description: 尝试批次性发送kafka消息
     * @Author: liuzhilin
     * @Date: 2025/4/6 11:06
     */
    public void retryFailedMessageList() {
        while (true) {
            List<KafkaMessage> failedMessageList = kafkaMessageService.getFailMessageList(null);

            if (CollectionUtils.isEmpty(failedMessageList)) {
                break; // 没有更多消息，退出循环
            }

            // 根据key进行分组,这里就不用管topic了
            Map<String, List<KafkaMessage>> keyMessageMap = failedMessageList.stream().collect(Collectors.groupingBy(KafkaMessage::getMessageKey));

            // 发送单key的消息
            keyMessageMap.entrySet().forEach(entry -> {
                try {
                    appExecutor.submit(() -> retrySingleKeyMessage(entry.getValue()));
                } catch (Exception e) {
                    log.error("submit retry task fail, messageKey: {}, error: {}", entry.getKey(), e.getMessage());
                }
            });

        }
    }

    /**
     * @Description: 重试发送单个key的kafka消息列表
     * @Author: liuzhilin
     * @Date: 2025/4/6 11:06
     */
    private void retrySingleKeyMessage(List<KafkaMessage> singleKeyMessageList) {
        try {
            log.info("retry single key size: " + singleKeyMessageList.size());

            singleKeyMessageList.forEach(message -> {
                CompletableFuture<SendResult<String, String>> singleMessageFuture = kafkaTemplate.send(message.getTopic(), message.getMessageKey(), message.getMessageContent());

                singleMessageFuture.thenAccept(result -> {
                    kafkaMessageService.updateSuccessMessageStatus(message.getId());
                }).exceptionally(ex -> {
                    message.setStatus(KafkaMessageStatusEnum.SEND_FAIL.getStatus());
                    kafkaMessageService.updateById(message);
                    log.error("retry kafka message fail. topic: {}, key: {}, error: {}", message.getTopic(), message.getMessageKey(), ex.getMessage());
                    return null;
                });

            });

        } catch (Exception e) {
            System.out.println("retry kafka message fail:" + e.getMessage());
        }
    }
}
