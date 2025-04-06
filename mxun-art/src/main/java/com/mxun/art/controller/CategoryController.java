package com.mxun.art.controller;

import com.mxun.art.entity.Tag;
import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.mxun.art.entity.Category;
import com.mxun.art.service.CategoryService;

import java.util.List;

/**
 * 用户创建的分类表 控制层。
 * @author moxuan
 * @since 2025-03-31
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("createCategory")
    public Category createCategory(@RequestParam("name") String name) {
        return categoryService.createCategory(name);
    }

    @GetMapping("getCategoryList")
    public List<Category> getCategoryList() {
        return categoryService.getCategoryList();
    }

}
