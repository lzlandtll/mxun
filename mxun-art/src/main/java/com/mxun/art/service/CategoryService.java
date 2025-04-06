package com.mxun.art.service;

import com.mybatisflex.core.service.IService;
import com.mxun.art.entity.Category;

import java.util.List;

/**
 * 用户创建的分类表 服务层。
 *
 * @author moxuan
 * @since 2025-03-31
 */
public interface CategoryService extends IService<Category> {

    List<Category> getCategoryList();

    Category createCategory(String name);
}
