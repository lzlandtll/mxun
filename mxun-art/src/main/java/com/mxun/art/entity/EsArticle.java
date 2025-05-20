package com.mxun.art.entity;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/26
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class EsArticle implements Serializable {

    private Long id;

    private String title;

    private String summary;

    private Long authorId;

    private Long categoryId;

    private Long likeCount;

    private Long collectionCount;

    private Long viewCount;

    private Long commentCount;

    private Long status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDeleted;
}
