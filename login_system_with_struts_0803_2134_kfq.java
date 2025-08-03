// 代码生成时间: 2025-08-03 21:34:48
package com.example.login;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;

/*
 * User login action class implementing ModelDriven interface
 * to hold the user login data.
 */
public class LoginAction extends ActionSupport implements ModelDriven<User> {

    /*
     * User object to hold login data.
     */
    private User user = new User();

    /*
     * Getter method for the user object.
     */
    public User getUser() {
        return user;
    }

    /*
     * Setter method for the user object.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /*
     * Method to validate user login.
     * Returns SUCCESS if credentials are valid, otherwise LOGIN.
     */
    public String login() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String username = user.getUsername();
        String password = user.getPassword();
        try {
            // Simulate database lookup (replace with actual database call)
            if ("admin".equals(username) && "password".equals(password)) {
                request.getSession().setAttribute("user", user);
                return SUCCESS;
            } else {
                return LOGIN;
            }
        } catch (Exception e) {
            addActionError("An error occurred during login: " + e.getMessage());
            return LOGIN;
        }
    }

    /*
     * Method to handle the login failure.
     */
    public String showLoginForm() {
        return LOGIN;
    }
}

/*
 * User model class to hold username and password.
 */
class User {
    private String username;
    private String password;

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
}