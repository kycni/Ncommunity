package com.kycni.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * @author Kycni
 * @date 2022/2/10 20:19
 */
@Repository
@Primary
public class HibernateImpl implements AlphaDao{
    @Override
    public String select() {
        return "Hibernate";
    }
}
