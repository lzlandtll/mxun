package com.mxun.sys.mapper;

import cn.hutool.db.sql.Query;
import com.mybatisflex.core.BaseMapper;
import com.mxun.sys.entity.KafkaMessage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  映射层。
 *
 * @author moxuan
 * @since 2025-04-05
 */
public interface KafkaMessageMapper extends BaseMapper<KafkaMessage> {

    @Select(" select * " +
            " from sys_kafka_message " +
            " where is_deleted = 0 " +
            "   and status = #{status} " +
            "   and create_time > #{startTime} " +
            "   and id > #{lastId}" +
            " limit #{pageSize}" +
            " for update")
    List<KafkaMessage> getFailMessageList(@Param("status") String status
            , @Param("startTime") LocalDateTime startTime
            , @Param("lastId") Long lastId
            , @Param("pageSize") Integer pageSize);
}
