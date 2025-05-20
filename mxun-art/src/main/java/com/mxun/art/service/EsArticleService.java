package com.mxun.art.service;

import com.mxun.art.entity.EsArticle;
import com.mxun.art.vo.req.ParamVO;
import com.mxun.art.vo.resp.OpenArticleVO;
import org.springframework.data.domain.Page;

import java.io.IOException;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/26
 */
public interface EsArticleService {
    Page<OpenArticleVO> searchArticle(ParamVO keyword) throws IOException;
}
