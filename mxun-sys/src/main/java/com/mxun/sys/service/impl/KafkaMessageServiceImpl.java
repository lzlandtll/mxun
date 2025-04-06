package com.mxun.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.mxun.common.constant.RedisConstant;
import com.mxun.common.enums.KafkaMessageStatusEnum;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.sys.entity.KafkaMessage;
import com.mxun.sys.mapper.KafkaMessageMapper;
import com.mxun.sys.service.KafkaMessageService;
import jakarta.annotation.PostConstruct;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *  服务层实现。
 *
 * @author moxuan
 * @since 2025-04-05
 */
@Service
public class KafkaMessageServiceImpl extends ServiceImpl<KafkaMessageMapper, KafkaMessage> implements KafkaMessageService {

    @Value("${spring.application.name}")
    private String moduleName;

    private String failMessageCursorKey;

    @Value("${custom.kafka.singleRetryFailMessageSize:2}")
    private int singleRetryFailMessageSize;

    @Autowired
    private RedissonClient redissonClient;

    @PostConstruct
    public void init() {
        // 在依赖注入完成后初始化 failMessageCursorKey
        failMessageCursorKey = String.join(":", RedisConstant.FAIL_KAFKA_CURSOR, moduleName);
    }

    private LocalDateTime getDefaultStartTime() {
        return LocalDateTime.now().minusDays(7);
    }

    @Override
    public void saveFailedMessage(String topic, String messageKey, Object objectMessage) {
        KafkaMessage failedMessage = new KafkaMessage();
        failedMessage.setTopic(topic);
        failedMessage.setMessageKey(messageKey);
        failedMessage.setMessageContent(JSON.toJSONString(objectMessage)); // 转换为字符串存储
        failedMessage.setMessageType(objectMessage.getClass().getName());
        failedMessage.setStatus(KafkaMessageStatusEnum.SEND_FAIL.getStatus()); // 标记为未成功发送
        save(failedMessage);
    }

    @Transactional
    @Override
    public List<KafkaMessage> getFailMessageList(LocalDateTime startTime) {

        RBucket<Long> bucket = redissonClient.getBucket(failMessageCursorKey);
        Long lastId = bucket.get();
        if (lastId == null) {
            lastId = 0L;
        }

        if (Objects.isNull(startTime)) {
            startTime = getDefaultStartTime();
        }

        List<KafkaMessage> failMessageList = mapper.getFailMessageList(KafkaMessageStatusEnum.SEND_FAIL.getStatus(), startTime, lastId, singleRetryFailMessageSize);

        if(CollectionUtils.isEmpty(failMessageList)){
            return null;
        }

        failMessageList.forEach(kafkaMessage -> kafkaMessage.setStatus(KafkaMessageStatusEnum.SENDING_IN_PROGRESS.getStatus()));

        updateBatch(failMessageList);

        Long newLastId = failMessageList.get(failMessageList.size() - 1).getId();
        bucket.set(newLastId); // 将新的游标值存储到 Redis

        return failMessageList;
    }

    @Override
    public void updateSuccessMessageStatus(Long id) {
        System.out.println("send success kafka message, id: " + id);
        KafkaMessage kafkaMessage = KafkaMessage.builder()
                .status(KafkaMessageStatusEnum.SEND_SUCCESS.getStatus())
                .build();
        kafkaMessage.setId(id);
        updateById(kafkaMessage);

        RBucket<Long> bucket = redissonClient.getBucket(failMessageCursorKey);
        Long lastId = bucket.get();


        // 查询数据库是否存在比当前游标更小的数据,没有则更新游标为当前id
        QueryWrapper query = this.query();
        query.ge(KafkaMessage::getCreateTime, getDefaultStartTime());
        if (lastId != null) {
            query.ge(KafkaMessage::getId, lastId);
        }
        query.lt(KafkaMessage::getId, id);
        query.in(KafkaMessage::getStatus, Arrays.asList(KafkaMessageStatusEnum.SEND_FAIL.getStatus(), KafkaMessageStatusEnum.SENDING_IN_PROGRESS.getStatus()));
        boolean exists = exists(query);

        if(!exists){
            bucket.set(id);
        }
    }
}
