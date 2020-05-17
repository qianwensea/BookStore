package com.shi.service.impl;

import com.shi.dao.UserDao;
import com.shi.dao.impl.UserDaoImpl;
import com.shi.pojo.User;
import com.shi.service.UserService;

/**
 * @author 千文sea
 * @create 2020-04-19 17:43
 */
public class UserServicImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public void registUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User login(User user) {
        return userDao.queryUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public boolean exitsUsername(String username) {
        if (userDao.queryUserByUsername(username) == null){
            return false;
        }
        return true;
    }
}
