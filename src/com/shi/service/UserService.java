package com.shi.service;

import com.shi.pojo.User;

/**
 * @author 千文sea
 * @create 2020-04-19 17:39
 */
public interface UserService {

    /**
     * 注册用户
     * @param user
     */
    public void registUser(User user);

    /**
     * 登录
     * @param user
     * @return 如果返回null,说明登入失败.如果有值就说明登录成功
     */
    public User login(User user);

    /**
     * 检查用户名是否可用
     * @param username
     * @return 返回true表示用户名已存在,返回false表示用户名可用
     */
    public boolean exitsUsername(String username);
}
