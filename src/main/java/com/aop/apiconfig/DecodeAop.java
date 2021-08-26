package com.aop.apiconfig;

import com.aop.dto.DataDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Decode(처리) -> Encode(출력)
 */

@Component
@Slf4j
@Aspect
public class DecodeAop {

    @Pointcut("@annotation(com.aop.annotation.Decode)")
    private void cut(){};

    @Before("cut()")
    public void beforeDecode(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof DataDto) {
                DataDto dto = (DataDto) arg;
                String content = dto.getContent();
                byte[] decoded = Base64.getDecoder().decode(content);

                dto.setContent(new String(decoded));

                MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                String methodName = signature.getMethod().getName();
                log.info("DecodeAop beforeDecode: methodName = {}, content = {}", methodName, dto.getContent());
            }
        }
    }

    @AfterReturning(value = "cut()", returning = "returnObj")
    public void afterEncode(JoinPoint joinPoint, Object returnObj) {
        if (returnObj instanceof DataDto) {
            DataDto dto = (DataDto) returnObj;
            String content = dto.getContent();

            String encoded = Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));

            dto.setContent(encoded);
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String methodName = signature.getMethod().getName();

            log.info("DecodeAop afterEncode: methodName = {}, content = {}", methodName, dto.getContent());
        }
    }
}
