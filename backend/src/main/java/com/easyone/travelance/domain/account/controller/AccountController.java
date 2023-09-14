package com.easyone.travelance.domain.account.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/account")
public class AccountController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
