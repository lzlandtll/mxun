package com.mxun.art.vo.resp;

import com.mxun.art.entity.Comment;
import lombok.Data;

import java.util.List;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/11
 */
@Data
public class RespCommentVO extends Comment {
    private String parentUserName;
    private String userName;
    private List<RespCommentVO> children;
}
