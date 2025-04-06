package com.mxun.sys.scheduler;

import com.mxun.sys.stream.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description: 定时任务,后面可能会换成xxljob
 * @Author: liuzhilin
 * @Date: 2025/4/6
 */
@Component
public class SysScheduler {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    /**
     * @Description: 每一小时执行失败消息重试
     * @Author: liuzhilin
     * @Date: 2025/4/6 18:21
     */
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void retryFailedMessages() {
        System.out.println("retry kafka message start");
        kafkaProducerService.retryFailedMessageList();
    }
}
