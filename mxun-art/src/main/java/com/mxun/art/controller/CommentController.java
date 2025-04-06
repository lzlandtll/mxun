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
import com.mxun.art.entity.Comment;
import com.mxun.art.service.CommentService;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 文章评论关联表 控制层。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 添加文章评论关联表。
     *
     * @param comment 文章评论关联表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody Comment comment) {
        return commentService.save(comment);
    }

    /**
     * 根据主键删除文章评论关联表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Long id) {
        return commentService.removeById(id);
    }

    /**
     * 根据主键更新文章评论关联表。
     *
     * @param comment 文章评论关联表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody Comment comment) {
        return commentService.updateById(comment);
    }

    /**
     * 查询所有文章评论关联表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<Comment> list() {
        return commentService.list();
    }

    /**
     * 根据文章评论关联表主键获取详细信息。
     *
     * @param id 文章评论关联表主键
     * @return 文章评论关联表详情
     */
    @GetMapping("getInfo/{id}")
    public Comment getInfo(@PathVariable Long id) {
        return commentService.getById(id);
    }

    /**
     * 分页查询文章评论关联表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<Comment> page(Page<Comment> page) {
        return commentService.page(page);
    }

}
