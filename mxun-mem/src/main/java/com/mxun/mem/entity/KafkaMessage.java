package com.mxun.mem.entity;

import com.mxun.common.core.CommonEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 *  实体类。
 *
 * @author moxuan
 * @since 2025-04-05
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data(staticConstructor = "create")
@EqualsAndHashCode(callSuper = true)
@Table("sys_kafka_message")
public class KafkaMessage extends CommonEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 消息主题
     */
    private String topic;

    /**
     * 消息key
     */
    private String messageKey;

    /**
     * 消息内容
     */
    @Column("messageContent")
    private String messageContent;

    /**
     * 消息类型
     */
    @Column("messageType")
    private String messageType;

    /**
     * 消息状态(01:发送失败,02:发送成功)
     */
    private String status;



}
