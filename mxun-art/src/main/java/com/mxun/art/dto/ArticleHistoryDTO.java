package com.mxun.art.dto;

import com.mxun.art.entity.ArticleHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleHistoryDTO extends ArticleHistory {
    private List<Long> tagIds;
}
