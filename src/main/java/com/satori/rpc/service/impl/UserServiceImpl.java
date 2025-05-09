package com.satori.rpc.service.impl;

import com.satori.rpc.common.User;
import com.satori.rpc.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(Integer id) {
        User user = User.builder().id(id).userName("satori").sex(true).build();
        System.out.println("查询到了"+id+"用户");
        return user;
    }

    @Override
    public Integer insertUserId(User user) {
        System.out.println("插入成功"+user);
        return 1;
    }

    @Override
    public String hello() {
        return "helloWorld";
    }
}
