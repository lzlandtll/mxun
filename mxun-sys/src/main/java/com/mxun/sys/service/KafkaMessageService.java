package com.mxun.sys.service;

import com.mybatisflex.core.service.IService;
import com.mxun.sys.entity.KafkaMessage;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  服务层。
 *
 * @author moxuan
 * @since 2025-04-05
 */
public interface KafkaMessageService extends IService<KafkaMessage> {

    // 保存发送失败的kafka消息
    void saveFailedMessage(String topic, String messageKey, Object objectMessage);

    // 获取一批发送失败的kafka消息,startTime防止全表扫描
    List<KafkaMessage> getFailMessageList(LocalDateTime startTime);

    // 更新kafka消息状态
    void updateSuccessMessageStatus(Long id);
}
