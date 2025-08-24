// 代码生成时间: 2025-08-25 05:05:28
package com.example.validation;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringValidator;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * FormValidator class to validate form data using Struts framework.
 */
@Namespace("/validation")
@Results({
    @Result(name = "success", type = "json"),
    @Result(name = "input", location = "/form-validation.jsp")
})
public class FormValidator extends ActionSupport {

    private String username;
    private String password;
    private String email;

    // Validate username
    @RequiredStringValidator(message = "Username is required.")
    @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters.")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Validate password
    @RequiredFieldValidator(message = "Password is required.")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters.")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Validate email
    @RequiredFieldValidator(message = "Email is required.")
    @Pattern(regexp = "^[a-zA-Z0-9_+&-]+(?:\.[a-zA-Z0-9_+&-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$", message = "Invalid email format.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Validate form data and return success if valid, otherwise return input.
     * @return String
     */
    @Action(value = "validateForm")
    public String validateForm() {
        if (this.hasActionErrors()) {
            return INPUT;
        } else {
            return SUCCESS;
        }
    }
}
