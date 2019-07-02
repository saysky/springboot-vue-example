package com.example.sens.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 言曌
 * @date 2019-05-08 13:36
 */

@RestController
public class MainController {

    @GetMapping
    public String index() {
        return "hello";
    }
}
