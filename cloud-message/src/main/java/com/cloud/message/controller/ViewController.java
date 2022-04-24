package com.cloud.message.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ycs
 * @description
 * @date 2022/3/21 10:57
 */
@Controller
public class ViewController {
    @GetMapping("/index")
    public String index(){
        return "index";
    }
}
