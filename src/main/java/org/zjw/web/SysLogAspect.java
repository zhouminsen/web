package org.zjw.web;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 系统日志，切面处理类
 *
 * @author
 * @email
 * @date
 */
@Aspect
@Component
public class SysLogAspect {

    @Pointcut("@annotation(org.zjw.web.annotation.Log)")//指向自定义注解路径
    public void logPointCut() {

    }

    /**
     * 切面记录系统日志
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("logPointCut()")//
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        return result;
    }

//保存日志
}
