package com.mxun.art.vo.resp;

import com.mxun.art.entity.Article;
import com.mxun.art.entity.Tag;
import lombok.Data;

import java.util.List;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/2
 */
@Data
public class OpenArticleVO extends Article {

    private String authorName;
    private String categoryName;
    private Boolean isFavorite = false;
    private Boolean isLike = false;
    private Boolean isFollow = false;
    private List<Tag> tagList;

}
