package com.shi.dao;

import com.shi.pojo.User;

/**
 * @author 千文sea
 * @create 2020-04-19 17:03
 */
public interface UserDao {


    /**
     * 通过用户名查询用户信息
     * @param username 用户名
     * @return 如果返回null,则说明没有这个用户,反之依然
     */
    public User queryUserByUsername(String username);
    /**
     * 保存用户信息
     * @param user
     * @return
     */
    public int saveUser(User user);

    /**
     * 根据用户名和密码查询用户信息
     * @param username
     * @param password
     * @return  如果返回null说明用户名或密码错误
     */
    public User queryUserByUsernameAndPassword(String username,String password);

}
