package com.cnpmnc.roms;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    @GetMapping("/HelloWorld")
    public String sayHello() {
        return "Hello World!";
    }

}
