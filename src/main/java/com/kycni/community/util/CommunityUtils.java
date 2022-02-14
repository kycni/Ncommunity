package com.kycni.community.util;

import com.sun.mail.smtp.DigestMD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author Kycni
 * @date 2022/2/14 8:47
 */
public class CommunityUtils {

    // 生成随机字符串
    public static String generateUUID () {
        return UUID.randomUUID().toString().replaceAll("-","");
    }    
    
    // MD5算法加密（只能加密不能解密）
    // 字符串加密值固定 (不安全) ，例如key=hello是我们要加密的数据，一般采用hello + a4sd8 加随机码方式加密 
    public static String md5 (String key) {
        //如果参数为空，则不用加密了，避免浪费资源
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes(StandardCharsets.UTF_8));
    }
}
