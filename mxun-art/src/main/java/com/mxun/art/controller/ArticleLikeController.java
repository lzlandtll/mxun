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
import com.mxun.art.entity.ArticleLike;
import com.mxun.art.service.ArticleLikeService;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 文章点赞表 控制层。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@RestController
@RequestMapping("/articleLike")
public class ArticleLikeController {

    @Autowired
    private ArticleLikeService articleLikeService;

    /**
     * 添加文章点赞表。
     *
     * @param articleLike 文章点赞表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody ArticleLike articleLike) {
        return articleLikeService.save(articleLike);
    }

    /**
     * 根据主键删除文章点赞表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Long id) {
        return articleLikeService.removeById(id);
    }

    /**
     * 根据主键更新文章点赞表。
     *
     * @param articleLike 文章点赞表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody ArticleLike articleLike) {
        return articleLikeService.updateById(articleLike);
    }

    /**
     * 查询所有文章点赞表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<ArticleLike> list() {
        return articleLikeService.list();
    }

    /**
     * 根据文章点赞表主键获取详细信息。
     *
     * @param id 文章点赞表主键
     * @return 文章点赞表详情
     */
    @GetMapping("getInfo/{id}")
    public ArticleLike getInfo(@PathVariable Long id) {
        return articleLikeService.getById(id);
    }

    /**
     * 分页查询文章点赞表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<ArticleLike> page(Page<ArticleLike> page) {
        return articleLikeService.page(page);
    }

}
