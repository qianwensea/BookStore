package com.shi.junit;

import com.shi.pojo.User;
import com.shi.web.UserServlet;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 千文sea
 * @create 2020-04-25 14:50
 */
public class UserServletTest {
    public void login(){
        System.out.println("这是login()方法调用了");
    }
    public void regist(){
        System.out.println("这是regist()方法调用了");
    }
    public void updateUser(){
        System.out.println("这是updateUser()方法调用了");
    }
    public void updateUserPassword(){
        System.out.println("这是updateUserPassword()()方法调用了");
    }

    public static void main(String[] args) {
        String action = "login";
        try {
            //获取action业务鉴别字符串,获取相应的业务方法反射对象
            Method method = UserServletTest.class.getDeclaredMethod(action);

            System.out.println(method);
            //调用目标业务方法
            method.invoke(new UserServletTest());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
