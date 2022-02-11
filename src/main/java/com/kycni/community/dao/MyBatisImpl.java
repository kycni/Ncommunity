package com.kycni.community.dao;

import org.springframework.stereotype.Repository;

/**
 * @author Kycni
 * @date 2022/2/10 20:13
 */
@Repository("MyBatis")
public class MyBatisImpl implements AlphaDao{
    @Override
    public String select() {
        return "MyBatis";
    }
}
