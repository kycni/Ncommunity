package com.kycni.community.service;

import com.kycni.community.dao.DiscussPostMapper;
import com.kycni.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Kycni
 * @date 2022/2/12 22:25
 */
@Service
public class DiscussPostService {
    
    @Autowired
    private DiscussPostMapper discussPostMapper;
    
    public List<DiscussPost> findDiscussPosts (int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(0, 0, 10);
    }

    public int findDiscussRows(int userId) {
        return discussPostMapper.selectDiscussRows(0);
    }
}
