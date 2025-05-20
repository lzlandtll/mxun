package com.mxun.common.config;


import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.mxun.common.constant.Constants;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ClassName: LocalDateTimeConfig
 * Description:
 *
 * @author chenjun
 * @date: 2023/6/13 15:09
 */
@Configuration
public class LocalDateTimeConfig {

    /**
     * 转化时间datetime
     * */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer dateAndTimeCustomizer() {
        LocalDateTimeSerializer timeSerializer = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_SECOND));
        return builder -> builder.serializerByType(LocalDateTime.class, timeSerializer);
    }

    /**
     * 转化时间date
     * */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer dateCustomizer() {
        LocalDateSerializer localDateSerializer = new LocalDateSerializer(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_DAY));
        return builder -> builder.serializerByType(LocalDate.class, localDateSerializer);
    }
}
