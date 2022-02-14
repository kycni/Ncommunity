package com.kycni.community.controller;

import com.kycni.community.service.AlphaService;
import com.kycni.community.util.CommunityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
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

    // @RequestParam响应Get请求，获取Get请求参数，/student?current&limit
    // @RequestParam的作用是获取前端Url？后面传入的参数的部分
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String students (
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "1") int limit) {
        System.out.println("limit: " + limit + "," + "current: " + current);
        return "@RequestParam响应GET请求";
    }

    // @PathVariable响应Get请求，获取Get的请求参数，student/1
    // @PathVariable作用是获取跳转Url路径中的可变参数
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String student (@PathVariable(name = "id") Integer id) {
        return "@PathVariable响应GET请求，id为：" + id;
    }

    // 响应POST请求（不用GET原因，显性传参，不安全，URL过长不方便）
    // 获取表单提交数据
    @RequestMapping(path = "/teacher1", method = RequestMethod.POST)
    @ResponseBody
    public String teacherForm (String name, Integer age) {
        System.out.println("姓名为：" + name + "，年龄为：" + age);
        return "POST获取表单信息成功";
    }
    
    // 响应HTML数据 (ModelAndView方式)
    @RequestMapping("/ModelAndView")
    public ModelAndView setStudent () {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "宋妮");
        mav.addObject("age", "18");
        mav.setViewName("/demo/view");
        return mav;
    }
    
    // ModelAndView简化版
    @RequestMapping(path = "Model", method = RequestMethod.GET)
    public String setManager (Model model) {
        model.addAttribute("name","管理者");
        model.addAttribute("age",26);
        return "/demo/view";
    }
    
    // 响应Json数据 (异步请求，如注册重复昵称检测)
    // Java对象 -> Json字符串 -> Js对象
    // 单Json数据
    @RequestMapping(path = "cosmetic", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getValentine () {
        Map<String, Object> valentine = new HashMap<>();
        valentine.put("口红", 10);
        valentine.put("粉底", "雅诗兰黛");
        valentine.put("面膜", 20);
        return valentine;
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
    
    // cookie演示案例
    @RequestMapping(path = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody
    public String setCookie (HttpServletRequest request, HttpServletResponse response) {
        // 创建Cookie
        Cookie cookie = new Cookie("token", CommunityUtils.generateUUID());
        // 设置Cookie生效范围
        cookie.setPath("/community/admin");
        // 设置Cookie生效时间（十分钟）
        cookie.setMaxAge(60 * 10);
        response.addCookie(cookie);
        return "set cookie";
    }
    
    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie (@CookieValue("token") String token) {
        System.out.println(token);
        return "get cookie";
    }
    
} 
