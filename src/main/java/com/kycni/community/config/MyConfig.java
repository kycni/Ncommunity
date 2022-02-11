package com.kycni.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

/**
 * @author Kycni
 * @date 2022/2/10 21:26
 */
@Configuration
public class MyConfig {
    
    @Bean
    public SimpleDateFormat simpleDateFormat () {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        
    }
}
