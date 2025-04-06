package com.mxun.sys.stream;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/6
 */

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "test-topic", groupId = "kafka-consumer-group")
    public void consume(String message) {
        System.out.println("接收到消息：" + message);
    }
}
