package com.example.demo;

import java.net.InetAddress;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public Map<String, String> home() throws Exception {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", "Hello from Java Spring Boot on KIND!");
        response.put("pod", InetAddress.getLocalHost().getHostName());
        response.put("version", "1.0.0");
        return response;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, DevOps!";
    }
}
