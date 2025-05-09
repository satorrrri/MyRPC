package com.satori.rpc.service.impl;

import com.satori.rpc.common.Blog;
import com.satori.rpc.service.BlogService;

public class BlogServiceImpl implements BlogService {

    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder().id(id).title("MyBlog").useId(6657).build();
        System.out.println("查询到了"+id+"博客");
        return blog;
    }
}
