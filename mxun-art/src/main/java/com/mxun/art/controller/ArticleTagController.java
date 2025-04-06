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
import com.mxun.art.entity.ArticleTag;
import com.mxun.art.service.ArticleTagService;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 文章标签关联表 控制层。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@RestController
@RequestMapping("/articleTag")
public class ArticleTagController {

    @Autowired
    private ArticleTagService articleTagService;

    /**
     * 添加文章标签关联表。
     *
     * @param articleTag 文章标签关联表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody ArticleTag articleTag) {
        return articleTagService.save(articleTag);
    }

    /**
     * 根据主键删除文章标签关联表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Long id) {
        return articleTagService.removeById(id);
    }

    /**
     * 根据主键更新文章标签关联表。
     *
     * @param articleTag 文章标签关联表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody ArticleTag articleTag) {
        return articleTagService.updateById(articleTag);
    }

    /**
     * 查询所有文章标签关联表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<ArticleTag> list() {
        return articleTagService.list();
    }

    /**
     * 根据文章标签关联表主键获取详细信息。
     *
     * @param id 文章标签关联表主键
     * @return 文章标签关联表详情
     */
    @GetMapping("getInfo/{id}")
    public ArticleTag getInfo(@PathVariable Long id) {
        return articleTagService.getById(id);
    }

    /**
     * 分页查询文章标签关联表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<ArticleTag> page(Page<ArticleTag> page) {
        return articleTagService.page(page);
    }

}
