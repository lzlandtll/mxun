package com.mxun.art.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.art.entity.Tag;
import com.mxun.art.mapper.TagMapper;
import com.mxun.art.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 关于文章的标签表 服务层实现。
 * @author moxuan
 * @since 2025-03-30
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public List<Tag> getTagList(String name) {
        QueryWrapper query = this.query();
        if(StringUtils.isNotBlank(name)){
            query.like(Tag::getName, name);
        }
        return this.list(query);
    }
}
