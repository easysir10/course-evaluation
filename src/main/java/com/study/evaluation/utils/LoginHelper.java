package com.study.evaluation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * description
 *
 * @author Hu.Wang 2020/04/01 13:43
 */
@Component
@Aspect
public class LoginHelper {
    private static Logger logger = LogManager.getLogger(LoginHelper.class);

    @Pointcut("within(com.study.evaluation.controller..*)&&!within(com.study.evaluation.controller.LoginController)")
    public void login() {
    }

    @Around("login()")
    public Object auth(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Object user = request.getSession().getAttribute("user");

        if (user==null) {
            logger.info("登录失效或尚未登录！");
            request.getSession().setAttribute("message","登录失效或尚未登录！");
            return "redirect:/";
        }else {
            logger.info(user.toString());
        }
        return joinPoint.proceed();

    }
}
