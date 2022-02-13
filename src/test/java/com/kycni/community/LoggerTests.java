package com.kycni.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Kycni
 * @date 2022/2/13 16:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = NcommunityApplication.class)
public class LoggerTests {
    private static final Logger logger = LoggerFactory.getLogger(LoggerTests.class);
    
    @Test
    public void testLogger () {
        System.out.println(logger.getName());
        
        logger.debug("debug level");
        logger.info("info level");
        logger.warn("warn level");
        logger.error("error log");
    }
}
