package com.mxun.art.vo;

import com.mxun.art.entity.ArticleHistory;
import lombok.Data;

import java.util.List;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/2
 */
@Data
public class EditArticleHistoryVO extends ArticleHistory {
    private List<Long> tagIds;

}
