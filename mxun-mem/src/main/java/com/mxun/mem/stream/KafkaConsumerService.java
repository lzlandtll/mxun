package com.mxun.mem.stream;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.nacos.common.utils.JacksonUtils;
import com.mxun.common.resultView.BusinessException;
import com.mxun.mem.dto.SyncUserDTO;
import com.mxun.mem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/6
 */
@Slf4j
@Service
public class KafkaConsumerService {

    @Autowired
    private UserService userService;

    @KafkaListener(topics = "${custom.kafka.sync-user-topic}", groupId = "${spring.application.name}")
    public void consume(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        String value = record.value();
        if(Objects.nonNull(value) && StringUtils.isNotBlank(value)){
            try {

                SyncUserDTO syncUserDTO = JacksonUtils.toObj(value, SyncUserDTO.class);
                userService.syncSysUser(syncUserDTO);
                // 保存后手动确认
                acknowledgment.acknowledge();
                log.info("sync kafka user data success");
            } catch (Exception e) {
                acknowledgment.nack(Duration.ofMillis(1000));
                log.error("sync kafka user data error:{}", JacksonUtils.toJson(value), e);
            }
        }
    }
}
