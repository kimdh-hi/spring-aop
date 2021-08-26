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

/**
 * Encode(처리) -> Decode(출력)
 */

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
                String encoded = Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));

                dto.setContent(String.valueOf(encoded));
                log.info("beforeEncode content = {}", dto.getContent());
            }
        }
    }

    @AfterReturning(value = "cut()", returning = "returnObj")
    public void afterDecode(JoinPoint joinPoint, Object returnObj) {
        DataDto dto = (DataDto) returnObj;
        String content = dto.getContent();
        log.info("Decode 전 afterReturning content = {}", dto.getContent());
        byte[] decoded = Base64.getDecoder().decode(content);

        dto.setContent(new String(decoded));
        log.info("Decode 후 afterReturning content = {}", dto.getContent());
    }

}
