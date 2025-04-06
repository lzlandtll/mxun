package com.mxun.art.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.mxun.common.enums.ErrorEnum;
import com.mxun.common.resultView.BusinessException;
import com.mxun.common.utils.UserUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.art.entity.Category;
import com.mxun.art.mapper.CategoryMapper;
import com.mxun.art.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 用户创建的分类表 服务层实现。
 *
 * @author moxuan
 * @since 2025-03-31
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public List<Category> getCategoryList() {
        QueryWrapper query = this.query();
        query.eq(Category::getUserId, UserUtil.getUserId());
        return this.list(query);
    }

    @Override
    public Category createCategory(String name) {
        if(StringUtils.isEmpty(name)){
            throw new BusinessException(ErrorEnum.ART_CATEGORY_NAME_NOT_BLANK_ERROR);
        }
        QueryWrapper query = this.query();
        query.eq(Category::getUserId, UserUtil.getUserId());
        query.eq(Category::getName, name);
        if(Objects.nonNull(this.getOne(query))){
            throw new BusinessException(ErrorEnum.ART_CATEGORY_NAME_ALREADY_EXIST_ERROR);
        }
        Category category = new Category();
        category.setName(name);
        category.setUserId(UserUtil.getUserId());
        this.save(category);
        return category;
    }
}
