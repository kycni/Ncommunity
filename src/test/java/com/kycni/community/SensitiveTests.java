package com.kycni.community;

import com.kycni.community.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Kycni
 * @date 2022/2/16 13:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = NcommunityApplication.class)
public class SensitiveTests {
    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Test
    public void testSensitiveFilter () {
        String text = "我们⭐不要⭐赌⭐博⭐，不⭐要⭐吸⭐毒，不要⭐嫖⭐娼⭐";
        System.out.println(sensitiveFilter.filter(text));
    }
}
