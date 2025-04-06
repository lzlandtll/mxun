package com.mxun.art.service;

import com.mybatisflex.core.service.IService;
import com.mxun.art.entity.Tag;

import java.util.List;

/**
 * 关于文章的标签表 服务层。
 *
 * @author moxuan
 * @since 2025-03-30
 */
public interface TagService extends IService<Tag> {

    List<Tag> getTagList(String name);
}
