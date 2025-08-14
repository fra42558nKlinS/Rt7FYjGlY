// 代码生成时间: 2025-08-15 05:02:04
package com.example.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

// 主题切换Action类
@Results({
    @Result(name = "success", type = "redirectAction", params = {"actionName", "default", "namespace", "/default"}),
    @Result(name = "error", location = "/error.jsp")
})
public class ThemeSwitcherAction extends ActionSupport implements SessionAware {

    private Map<String, Object> session;
    private String theme;

    // 设置主题
    public String setTheme() {
        try {
            if (theme != null && !theme.isEmpty()) {
                // 将主题设置到session中
                session.put("theme", theme);
            } else {
                // 没有指定主题，返回错误
                addActionError("Theme cannot be null or empty");
                return ERROR;
            }
        } catch (Exception e) {
            // 处理异常
            addActionError("Error occurred: " + e.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    // Getter和Setter方法
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
}
