package com.mxun.art.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.mxun.art.entity.Tag;
import com.mxun.art.service.TagService;

import java.util.List;

/**
 * 关于文章的标签表 控制层。
 * @author moxuan
 * @since 2025-03-30
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("getTagList")
    public List<Tag> getTagList(@RequestParam("name") String name) {
        return tagService.getTagList(name);
    }

}
