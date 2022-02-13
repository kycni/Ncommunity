package com.kycni.community.service;

import com.kycni.community.dao.UserMapper;
import com.kycni.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kycni
 * @date 2022/2/13 8:28
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public User getUserById (int id) {
        return userMapper.selectById(id);
    }
}
