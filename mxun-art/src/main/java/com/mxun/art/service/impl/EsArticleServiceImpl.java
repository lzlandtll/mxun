package com.mxun.art.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.mxun.art.dto.UserDTO;
import com.mxun.art.entity.Category;
import com.mxun.art.entity.EsArticle;
import com.mxun.art.entity.Tag;
import com.mxun.art.feign.SysFeignService;
import com.mxun.art.service.ArticleTagService;
import com.mxun.art.service.CategoryService;
import com.mxun.art.service.EsArticleService;
import com.mxun.art.vo.req.ParamVO;
import com.mxun.art.vo.resp.OpenArticleVO;
import com.mxun.common.resultView.ResultView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/26
 */
@Service
public class EsArticleServiceImpl implements EsArticleService {

    @Autowired
    private CategoryService categoryService;

    @Lazy
    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private SysFeignService sysFeignService;
    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private int pageSize = 8;

    /**
     * 高亮搜索
     */
    @Override
    public Page<OpenArticleVO> searchArticle(ParamVO paramVO) throws IOException {
        // 计算分页偏移量
        int from = (paramVO.getPageNum() - 1) * pageSize;

        Query query = Query.of(q -> q
                .bool(b -> {
                    // 关键词查询
                    Optional.ofNullable(paramVO.getQueryKey())
                            .filter(StringUtils::hasText)
                            .ifPresent(keyword -> b.must(m -> m
                                    .multiMatch(mm -> mm
                                            .fields("title", "summary")
                                            .query(keyword)
                                    )
                            ));

                    // 用户ID过滤
                    Optional.ofNullable(paramVO.getUserId())
                            .ifPresent(userId -> b.filter(f -> f
                                    .term(t -> t.field("author_id").value(userId))
                            ));

                    // 分类ID过滤
                    Optional.ofNullable(paramVO.getCategoryId())
                            .ifPresent(categoryId -> b.filter(f -> f
                                    .term(t -> t.field("category_id").value(categoryId))
                            ));
                    return b;
                })
        );

        // 配置高亮
        Map<String, HighlightField> highlightFields = new HashMap<>();
        highlightFields.put("title", HighlightField.of(h -> h.preTags("<font color=\"red\">").postTags("</font>")));
        highlightFields.put("summary", HighlightField.of(h -> h.preTags("<font color=\"red\">").postTags("</font>")));

        // 构建搜索请求
        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index("blogs")
                .query(query)
                .highlight(h -> h.fields(highlightFields))
                .from(from)  // 设置起始位置
                .size(pageSize)  // 设置每页大小
        );

        // 执行搜索
        SearchResponse<EsArticle> response = elasticsearchClient.search(searchRequest, EsArticle.class);

        // 解析结果
        List<OpenArticleVO> content = new ArrayList<>();
        for (Hit<EsArticle> hit : response.hits().hits()) {
            EsArticle esArticle = hit.source();

            // 替换高亮字段
            if (hit.highlight() != null) {
                if (hit.highlight().get("title") != null) {
                    esArticle.setTitle(hit.highlight().get("title").get(0));
                }
                if (hit.highlight().get("summary") != null) {
                    esArticle.setSummary(hit.highlight().get("summary").get(0));
                }
            }
            OpenArticleVO openArticleVO = new OpenArticleVO();
            BeanUtils.copyProperties(esArticle, openArticleVO);


            // 设置作者名称
            ResultView<UserDTO> resultView = sysFeignService.getUserById(openArticleVO.getAuthorId());
            UserDTO userDTO = resultView.getData();
            openArticleVO.setAuthorName(userDTO.getUsername());

            // 设置分类名称
            if(!Objects.isNull(openArticleVO.getCategoryId())){
                Category category = categoryService.getById(openArticleVO.getCategoryId());
                if(!Objects.isNull(category)){
                    openArticleVO.setCategoryName(category.getName());
                }
            }

            // 设置文章所属标签信息
            List<Tag> articleTagList = articleTagService.getListByArticleId(openArticleVO.getId());
            openArticleVO.setTagList(articleTagList);

            content.add(openArticleVO);
        }

        // 获取总记录数
        long totalHits = response.hits().total() != null ? response.hits().total().value() : 0;

        // 返回分页对象
        return new PageImpl<>(content, PageRequest.of((paramVO.getPageNum() - 1), pageSize), totalHits);
    }
}
