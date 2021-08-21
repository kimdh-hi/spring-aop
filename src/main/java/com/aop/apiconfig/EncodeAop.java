package com.aop.apiconfig;

import com.aop.controller.TestController;
import com.aop.dto.DataDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Aspect
@Component
public class EncodeAop {

    @Pointcut("@annotation(com.aop.annotation.Encode)")
    private void cut(){}


    @Before("cut()")
    public void beforeEncode(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof DataDto) {
                DataDto dto = (DataDto) arg;
                String content = dto.getContent();
                byte[] encoded = Base64.getEncoder().encode(content.getBytes(StandardCharsets.UTF_8));

                dto.setContent(String.valueOf(encoded));
                log.info("beforeEncode content = {}", dto.getContent());
            }
        }
    }

    @AfterReturning(value = "cut()", returning = "returnObj")
    public void afterDecode(JoinPoint joinPoint, Object returnObj) {
        DataDto dto = (DataDto) returnObj;
        String content = dto.getContent();
        byte[] encoded = Base64.getEncoder().encode(content.getBytes(StandardCharsets.UTF_8));

        dto.setContent(String.valueOf(encoded));
        log.info("afterReturning content = {}", dto.getContent());
    }

}
