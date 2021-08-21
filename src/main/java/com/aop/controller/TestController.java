package com.aop.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
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

    @AllArgsConstructor
    @Data
    static class DataDto {
        private String id;
        private String content;
    }
}
