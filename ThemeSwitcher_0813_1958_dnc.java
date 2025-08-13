// 代码生成时间: 2025-08-13 19:58:15
package com.example.struts2.themeswitcher;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

// 主题切换Action类
@Namespace("/theme")
@ParentPackage("default")
public class ThemeSwitcher extends ActionSupport {
    
    private String currentTheme;
    private final List<String> availableThemes = new ArrayList<>();
    
    // 默认可用的主题
    public ThemeSwitcher() {
        availableThemes.add("default");
        availableThemes.add("dark");
        availableThemes.add("light");
    }
    
    // 设置当前主题
    public void setCurrentTheme(String currentTheme) {
        this.currentTheme = currentTheme;
    }
    
    // 获取当前主题
    public String getCurrentTheme() {
        return currentTheme;
    }
    
    // 获取可用主题列表
    public List<String> getAvailableThemes() {
        return availableThemes;
    }
    
    // 切换主题方法
    @Action(value = "switchTheme", results = {
        @Result(name = SUCCESS, location = "/index.jsp")
    })
    public String switchTheme() {
        HttpSession session = ((HttpServletRequest) ActionContext.getContext().get("com.opensymphony.xwork2.util.HTTPRequest")).getSession();
        
        // 检查是否提供主题参数
        if (currentTheme == null || currentTheme.isEmpty()) {
            addActionError("No theme was provided.");
            return ERROR;
        }
        
        // 检查主题是否可用
        if (!availableThemes.contains(currentTheme)) {
            addActionError("The provided theme is not available.");
            return ERROR;
        }
        
        // 设置主题到session
        session.setAttribute("theme", currentTheme);
        return SUCCESS;
    }
}
