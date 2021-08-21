package com.aop.controller;

import com.aop.annotation.Encode;
import com.aop.annotation.ExeTimer;
import com.aop.dto.DataDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/echo")
    public String echo(@RequestParam String message) {

//        log.info("GET /echo parameter = {}", message);
//
//        log.info("GET /echo return = {}", message);
        return message;
    }

    @PostMapping("/save")
    public DataDto save(@RequestBody DataDto dto) {

//        log.info("POST : /save parameter = {}", dto.getId());
//        log.info("POST : /save parameter = {}", dto.getContent());
//
//        log.info("GET /save return = {}", dto.getId());
//        log.info("GET /save return = {}", dto.getContent());
        return dto;
    }

    // AOP를 통해 메서드 실행시간 측정을 위한 마크용 어노테이션
    @ExeTimer
    @GetMapping("/db-access")
    public String dbAccess() throws InterruptedException {

        Thread.sleep(1000); // DB 접근시 1초가 걸린다고 가정

        return "db";
    }

    // AOP를 이용한 인코딩 및 디코딩
    @Encode
    @GetMapping("/encode")
    public DataDto encode(@RequestBody DataDto dto) {

        return dto;
    }


}
