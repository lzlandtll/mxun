package com.mxun.sys.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.sys.entity.ApiResource;
import com.mxun.sys.mapper.ApiResourceMapper;
import com.mxun.sys.service.ApiResourceService;
import org.springframework.stereotype.Service;

/**
 * api资源表 服务层实现。
 *
 * @author moxuan
 * @since 2025-03-08
 */
@Service
public class ApiResourceServiceImpl extends ServiceImpl<ApiResourceMapper, ApiResource> implements ApiResourceService {

}
