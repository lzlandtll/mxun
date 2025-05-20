package com.mxun.art.vo.req;

import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/4
 */
@Data
public class ParamVO {
    private Long userId;
    private String queryKey;
    private Long categoryId;

    @Min(value = 1, message = "00006")
    private int pageNum;
}
