package com.kycni.community.controller;

import com.kycni.community.annotation.LoginRequired;
import com.kycni.community.controller.interceptor.LoginRequiredInterceptor;
import com.kycni.community.entity.User;
import com.kycni.community.service.UserService;
import com.kycni.community.util.CommunityUtils;
import com.kycni.community.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Kycni
 * @date 2022/2/15 18:39
 */
@Controller
@RequestMapping(path = "/user", method = RequestMethod.GET)
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Value("${community.path.domain}")
    private String domain;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${community.path.upload}")
    private String uploadPath;
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;
    
    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage () {
        return "/site/setting";
    }
    
    @LoginRequired
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader (MultipartFile headerImage, Model model) {
        // 对用户上传的文件进行判断
        if (headerImage == null) {
            model.addAttribute("error", "您还没有选择图片");
            return "/site/setting";
        }
        String filename = headerImage.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "文件格式不正确");
            return "/site/setting";  
        }
        
        // 生成随机文件名
        filename = CommunityUtils.generateUUID() + suffix;
        
        // 确定文件的存放位置
        File dest = new File(uploadPath + "/" + filename);
        try {
            // 存储文件
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败" + e.getMessage());
            throw new RuntimeException("上传文件失败,服务器发生异常" + e);
        }
        
        // 更新当前用户头像路径 (web路径)
        // localhost:8080/community/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + filename;
        userService.updateHeader(user.getId(), headerUrl);
        return "redirect:/index";
    }
    
    @RequestMapping(path = "/header/{filename}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("filename") String filename, HttpServletResponse response) {
        // 服务器存放的路径
        filename = uploadPath + "/" + filename;
        // 文件后缀
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        // 响应图片
        response.setContentType("image/" + suffix);
        try (
                FileInputStream fis = new FileInputStream(filename);
                OutputStream os = response.getOutputStream();
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
               os.write(buffer,0,b); 
            }
        } catch (IOException e) {
            logger.error("读取头像失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @RequestMapping(path = "/password", method = RequestMethod.POST)
    public String updatePassword (String password, String newPassword, 
                                  String reNewPassword, Model model) {
        if (!newPassword.equals(reNewPassword)) {
            return "/user/setting";
        }
        User user = hostHolder.getUser();
        userService.updatePassword(user.getId(),newPassword);
        return "redirect:/logout"; 
    }
}
