package com.kycni.community.controller;

import com.kycni.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Kycni
 * @date 2022/2/10 14:22
 */
@Controller
@ResponseBody
@RequestMapping("/admin")
public class HelloController {

    @Autowired
    private AlphaService alphaService;
    
    @RequestMapping ("/alpha")
    public String test () {
        return alphaService.find();
    }
}
