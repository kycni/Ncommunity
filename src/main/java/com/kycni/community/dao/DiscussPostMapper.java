package com.kycni.community.dao;

import com.kycni.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Kycni
 * @date 2022/2/12 18:17
 */
@Mapper
public interface DiscussPostMapper {
    
    /*动态SQL*/
    List<DiscussPost> selectDiscussPosts (int userId, int offset, int limit);

    // 在动态SQL中,只有一个变化的参数条件,这个时候必须要用@Param声明参数
    int selectDiscussRows (@Param("userId") int userId);
    
}
