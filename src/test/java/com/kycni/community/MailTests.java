package com.kycni.community;

import com.kycni.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author Kycni
 * @date 2022/2/13 19:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = NcommunityApplication.class)
public class MailTests {
    @Autowired
    private MailClient mailClient;
    @Autowired
    private TemplateEngine templateEngine;
    
    @Test
    public void testSendEmail () {
        mailClient.sendMail("syq278872032@gmail.com", "宝宝", "发邮件功能搞定！！");
    }
    
    @Test
    public void testSendHtml () {
        Context context = new Context();
        context.setVariable("username", "kycni");
        String content = templateEngine.process("/mail/demo", context);
        mailClient.sendMail("syq278872032@gmail.com", "HTML", content);
    }
}
