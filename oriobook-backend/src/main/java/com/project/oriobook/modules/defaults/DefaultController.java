package com.project.oriobook.modules.defaults;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }
}
