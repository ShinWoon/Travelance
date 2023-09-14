package com.easyone.travelance.domain.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/card")
public class CardController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
