package com.kycni.community.controller;

import com.kycni.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

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
    
    @RequestMapping ("/http")
    public void testHttp(HttpServletRequest req, HttpServletResponse resp) {
        String servletPath = req.getServletPath();
        System.out.println(servletPath);
        String method = req.getMethod();
        System.out.println(method);
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = req.getHeader(key);
            System.out.println(key + ":" + value);
        }
        System.out.println(req.getParameter("code"));
        
        // 返回响应数据
        resp.setContentType("text/html;charset=UTF-8");
        try (
                PrintWriter writer = resp.getWriter();

        ) {
            writer.write("<h1>琪琪喜欢妮妮<h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
