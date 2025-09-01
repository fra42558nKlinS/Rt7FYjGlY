// 代码生成时间: 2025-09-01 11:25:18
package com.example.login;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

// 用户登录验证Action类
public class UserLoginAction extends ActionSupport {

    // 用户名和密码属性
    private String username;
    private String password;

    // 实现getter和setter方法
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // 登录执行方法
    public String login() {
        HttpServletRequest request = ServletActionContext.getRequest();

        // 模拟数据库存储的用户信息
        Map<String, String> userStore = new HashMap<>();
        userStore.put("admin", "password123");

        // 检查用户名和密码是否匹配
        if (userStore.containsKey(username) && userStore.get(username).equals(password)) {
            request.getSession().setAttribute("user", username);
            return Action.SUCCESS;
        } else {
            // 设置错误信息
            addActionError("Invalid username or password");
            return Action.ERROR;
        }
    }

    // 退出登录
    public String logout() {
        ServletActionContext.getRequest().getSession().invalidate();
        return Action.SUCCESS;
    }
}
