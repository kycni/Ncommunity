package com.kycni.community.util;

import com.kycni.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * 持有用户信息，用来代替session对象（session对象是线程隔离的）
 * @author Kycni
 * @date 2022/2/15 15:57
 */
@Component
public class HostHolder {
    // User类线程map
    private ThreadLocal<User> users = new ThreadLocal<>();
    
    public void setUser (User user) {
        users.set(user);
    }
    
    public User getUser () {
        return users.get();
    }
    
    // 服务器处理请求后，并响应给客户端之后，线程销毁
    public void clear () {
        users.remove();
    }
}
