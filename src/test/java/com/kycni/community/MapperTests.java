package com.kycni.community;

import com.kycni.community.dao.UserMapper;
import com.kycni.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author Kycni
 * @date 2022/2/12 13:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = NcommunityApplication.class)
public class MapperTests {
    
    @Autowired
    private UserMapper userMapper;
    
    @Test
    public void TestSelect () {
        User user = userMapper.selectById(12);
        System.out.println(user);
        user = userMapper.selectByEmail("nowcoder12@sina.com");
        System.out.println(user);
        user = userMapper.selectByName("zhangfei");
        System.out.println(user);
    }
    
    @Test
    public void TestInsert () {
        User user = new User();
        user.setUsername("test1");
        user.setPassword("123456");
        user.setSalt("abc");
        user.setEmail("test@qq.com");
        user.setHeaderUrl("http://www.nowcoder.com/101.png");
        user.setCreateTime(new Date());
        user.setActivationCode("1234");
        user.setType(1);
        int rows = userMapper.insertUser(user);
        System.out.println(rows);
    }
    
    @Test
    public void TestUpdate () {
        int rows = userMapper.updateStatus(12, 1);
        rows = userMapper.updateHeaderUrl(12, "http://www.nowcoder.com/102.png");
        rows = userMapper.updatePassword(12, "123456");
    }
}
