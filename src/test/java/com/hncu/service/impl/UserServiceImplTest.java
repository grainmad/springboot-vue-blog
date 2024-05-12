package com.hncu.service.impl;

import com.hncu.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void test(){
        User yyy = userService.login("zhaoguoshun", "985211");
        System.out.println(yyy);
    }
}
