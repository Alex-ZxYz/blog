package com.ZxYz.aspect;

import com.ZxYz.annotation.SystemLog;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * 日志切面类
 */
@Aspect
@Slf4j
@Component
public class LogAspect {

    /**
     * 切点
     */
    @Pointcut("@annotation(com.ZxYz.annotation.SystemLog)")
    public void pt(){

    }

    /**
     *  环绕通知日志打印
     * @param joinPoint 切入点
     * @return
     */
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable{


        Object ret;
        try{
            handleBefore(joinPoint);
            ret =joinPoint.proceed();
            handleAfter(ret);
        }finally {
            // 结束后换行
            log.info("=======End=======" + System.lineSeparator());
        }

        return ret;
    }

    private void handleAfter(Object ret) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSON(ret));
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        //获取增强方法上的属性对象
        SystemLog systemLog = getSystemLog(joinPoint);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();


        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}", systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(),((MethodSignature)joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSON(joinPoint.getArgs()));
    }


    /**
     *  获取增强服务日志信息
     * @param joinPoint 切入点
     * @return 注释对象
     */
    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        //获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        //获取方法签名中注解对象并返回注解信息
        return methodSignature.getMethod().getAnnotation(SystemLog.class);

    }


}
