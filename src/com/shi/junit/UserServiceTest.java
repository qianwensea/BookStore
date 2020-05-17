package com.shi.junit;

import com.shi.pojo.User;
import com.shi.service.UserService;
import com.shi.service.impl.UserServicImpl;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author 千文sea
 * @create 2020-04-19 17:50
 */
public class UserServiceTest {
    UserService userService = new UserServicImpl();

    @Test
    public void registUser() {
        userService.registUser(new User(null,"aze","aze","aze@qq.com"));
    }

    @Test
    public void login() {
        System.out.println(userService.login(new User(null, "aze", "aze", null)));
    }

    @Test
    public void exitsUsername() {
        if (userService.exitsUsername("sjh")){
            System.out.println("用户名已存在!");
        }else
            System.out.println("用户名可用!");
    }
}