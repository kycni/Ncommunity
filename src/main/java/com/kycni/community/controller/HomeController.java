package com.kycni.community.controller;

import com.kycni.community.entity.DiscussPost;
import com.kycni.community.entity.Page;
import com.kycni.community.entity.User;
import com.kycni.community.service.DiscussPostService;
import com.kycni.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kycni
 * @date 2022/2/12 21:55
 */
@Controller
public class HomeController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private DiscussPostService discussPostService;
    
    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getDiscussPosts (Model model, Page page) {
        // SpringMVC中参数Model和Page由DispatcherServlet实例化，自动将Page装入Model
        page.setRows(discussPostService.findDiscussRows(0));
        System.out.println(page.getRows());
        
        page.setPath("/index");
        
        List<DiscussPost> posts = 
                discussPostService.findDiscussPosts(0, page.getOffset(),page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();

        if (posts != null) {
            for (DiscussPost post : posts) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);
                User user = userService.getUserById(post.getUserId());
                map.put("user", user);
                discussPosts.add(map); 
            }
        }
        
        model.addAttribute("discussPosts", discussPosts);
        
        return "/index";
    }
    
}
