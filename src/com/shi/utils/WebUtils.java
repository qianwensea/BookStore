package com.shi.utils;

import com.shi.pojo.User;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author 千文sea
 * @create 2020-04-25 16:49
 */
public class WebUtils {
    /**
     * 把 Map 中的值注入到对应的 JavaBean 属性中
     *
     * @param value
     * @param bean
     * @param <T>
     * @return bean
     */
    public static <T> T copyParamBean(Map value, T bean) {
        try {
            System.out.println("注入之前: " + bean);
            /**
             * 把所有请求的参数都注入到user对象中
             */
            BeanUtils.populate(bean, value);
            System.out.println("注入之后: " + bean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return bean;
    }


    public static int parseInt(String strInt,int defaultValue){
        try {
            if (strInt != null && !"".equals(strInt)){
                return Integer.parseInt(strInt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}
