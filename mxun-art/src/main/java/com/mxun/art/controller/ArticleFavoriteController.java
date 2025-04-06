package com.mxun.art.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.mxun.art.entity.ArticleFavorite;
import com.mxun.art.service.ArticleFavoriteService;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 文章收藏表 控制层。
 * @author moxuan
 * @since 2025-03-30
 */
@RestController
@RequestMapping("/articleCollection")
public class ArticleFavoriteController {

    @Autowired
    private ArticleFavoriteService articleCollectionService;

    /**
     * 添加文章收藏表。
     *
     * @param articleCollection 文章收藏表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody ArticleFavorite articleCollection) {
        return articleCollectionService.save(articleCollection);
    }

    /**
     * 根据主键删除文章收藏表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Long id) {
        return articleCollectionService.removeById(id);
    }

    /**
     * 根据主键更新文章收藏表。
     *
     * @param articleCollection 文章收藏表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody ArticleFavorite articleCollection) {
        return articleCollectionService.updateById(articleCollection);
    }

    /**
     * 查询所有文章收藏表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<ArticleFavorite> list() {
        return articleCollectionService.list();
    }

    /**
     * 根据文章收藏表主键获取详细信息。
     *
     * @param id 文章收藏表主键
     * @return 文章收藏表详情
     */
    @GetMapping("getInfo/{id}")
    public ArticleFavorite getInfo(@PathVariable Long id) {
        return articleCollectionService.getById(id);
    }

    /**
     * 分页查询文章收藏表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<ArticleFavorite> page(Page<ArticleFavorite> page) {
        return articleCollectionService.page(page);
    }

}
