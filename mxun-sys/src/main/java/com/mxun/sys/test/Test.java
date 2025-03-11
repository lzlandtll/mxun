package com.mxun.sys.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/2/25
 */
public class Test {
    public static void main(String[] args) {
        NewRetailSalesOrderDTO dto = new NewRetailSalesOrderDTO();
        dto.setTrade_id("123");
        dto.setOrd_code("456");
        dto.setWms_delivery_code("789");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        NewRetailSalesOrder newRetailSalesOrder = objectMapper.convertValue(dto, NewRetailSalesOrder.class);
        System.out.println("dto: " + dto);
        System.out.println("newRetailSalesOrder: " + newRetailSalesOrder);
    }
}
