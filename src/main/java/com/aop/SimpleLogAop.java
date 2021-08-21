package com.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SimpleLogAop {

    // com.aop.controller 이하 클래스에 적용
    @Pointcut("execution(* com.aop.controller..*.*(..))")
    private void cut(){}

    @Before("cut()")
    public void beforeParameterLog(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            log.info("parameter type = {}", arg.getClass().getSimpleName());
            log.info("parameter value = {}", arg);
        }
    }

    @AfterReturning(value = "cut()", returning = "returnObj")
    public void afterReturnLog(Object returnObj) {
        log.info("return type = {}", returnObj.getClass().getSimpleName());
        log.info("return value = {}", returnObj);
    }
}
