package com.kycni.community.dao;

import com.kycni.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author Kycni
 * @date 2022/2/12 10:45
 */
@Mapper
public interface UserMapper {
    
    User selectById (int id); 
    
    User selectByName (String userName);
    
    User selectByEmail (String email);
    
    int insertUser (User user);
    
    int updateStatus (int id, int status);
    
    int updateHeaderUrl (int id, String headerUrl);
    
    int updatePassword (int id, String password);
    
}
