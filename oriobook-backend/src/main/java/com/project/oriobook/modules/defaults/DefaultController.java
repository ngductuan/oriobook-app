package com.project.oriobook.modules.defaults;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/")
@Tag(name = "default")
public class DefaultController {
    @GetMapping("")
    public String hello() {
        return "Hello World!";
    }
}
