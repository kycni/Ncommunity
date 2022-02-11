package com.kycni.community.controller;

import com.kycni.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author Kycni
 * @date 2022/2/10 14:22
 */
@Controller
@RequestMapping("/admin")
public class HelloController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/alpha")
    public String test() {
        return alphaService.find();
    }
    
    // 模拟HTTP请求与相应信息
    @RequestMapping("/http")
    public void testHttp(HttpServletRequest req, HttpServletResponse resp) {
        //返回请求信息
        System.out.println(req.getServletPath());
        System.out.println(req.getMethod());
        
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
                PrintWriter writer = resp.getWriter()
        ) {
            writer.write("<h1>琪琪喜欢妮妮<h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // @RequestParam响应Get请求，获取Get请求参数，student?current&limit
    // @RequestParam的作用是获取前端Url传入的参数
    @ResponseBody
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    public String getStudents (
            @RequestParam(value = "current", required = false, defaultValue = "1") int current,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "测试前端请求参";
    }

    // @PathVariable响应Get请求，获取Get的请求参数，student/1
    // @PathVariable作用是获取指定Url路径的用{}包裹的可变部分
    @ResponseBody
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    public String getStudent(@PathVariable(value = "id") int id) {
        System.out.println(id);
        return "我是1号的学生信息";
    }

    // 响应POST请求（不用GET原因，显性传参，不安全，URL过长不方便）
    // 获取表单提交数据
    @ResponseBody
    @RequestMapping(path = "/student",method = RequestMethod.POST)
    public String saveStudent (String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return "返回成功！";
    }
    
    // 响应HTML数据 (ModelAndView方式)
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher () {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "宋妮");
        mav.addObject("age", 15);
        mav.setViewName("/demo/view");
        return mav;
    }
    
    // ModelAndView简化版
    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool (Model model) {
        model.addAttribute("name","辽宁石油化工大学");
        model.addAttribute("age",50);
        return "/demo/view";
    }
    
    // 响应Json数据 (异步请求，如注册重复昵称检测)
    // Java对象 -> Json字符串 -> Js对象
    // 单Json数据
    @RequestMapping(path = "/employ", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEmploy () {
        Map<String, Object> employ = new HashMap<>();
        employ.put("姓名", "宋琪");
        employ.put("年龄", 25);
        employ.put("薪资", 15000.00);
        return employ;
    }

    // 响应Json数据 (异步请求，如注册重复昵称检测)
    // 多Json数据
    @RequestMapping(path = "/employs", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmploys () {
        List<Map<String, Object>> employs = new ArrayList<>();
        Map<String, Object> employ = new HashMap<>();
        employ.put("姓名", "宋妮");
        employ.put("年龄", 25);
        employ.put("薪资", 15000.00);
        employs.add(employ);

        employ = new HashMap<>();
        employ.put("姓名", "妮琪");
        employ.put("年龄", 24);
        employ.put("薪资", 17000.00);
        employs.add(employ);
        return employs;
    }
    
} 
