package com.mxun.art.vo.req;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/10
 */
@Data
public class ReqCommentVO {

    @Nullable
    @Min(value = 1, message = "05012")
    private Long parentId;

    @NotNull(message = "05010")
    private Long articleId;

    @NotNull(message = "05011")
    private String content;
}
