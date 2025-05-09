package com.satori.rpc.service;

import com.satori.rpc.common.User;

public interface UserService {
    User getUserById(Integer id);

    Integer insertUserId(User user);

    String hello();
}
