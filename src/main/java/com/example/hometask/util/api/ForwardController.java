package com.example.hometask.util.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForwardController {
    @GetMapping(value = {"/search", "/users", "/cars"})
    public String frontend() {
        return "forward:/";
    }
}
