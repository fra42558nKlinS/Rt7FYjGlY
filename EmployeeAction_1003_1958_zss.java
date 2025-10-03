// 代码生成时间: 2025-10-03 19:58:47
package com.example.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.struts.service.EmployeeService;
import com.example.struts.model.Employee;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

// 定义员工Action类
@Namespace("/employee")
@Results(value = {
    @Result(name = "success", location = "/WEB-INF/views/employee-success.jsp"),
    @Result(name = "error", location = "/WEB-INF/views/employee-error.jsp")
})
public class EmployeeAction extends ActionSupport implements ModelDriven<Employee> {

    private EmployeeService employeeService;
    private Employee employee; // 数据模型

    // 自动注入EmployeeService
    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 获取数据模型
    @Override
    public Employee getModel() {
        return employee;
    }

    // 查找所有员工
    @Action(value = "list", results = {
        @Result(name = "success", type = "json")
    })
    public String list() {
        try {
            List<Employee> employees = employeeService.findAll();
            addActionMessage("Employees loaded successfully");
            // 将员工列表设置到模型中
            this.setEmployee(employees.get(0));
            // 返回JSON结果
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Failed to load employees");
            return ERROR;
        }
    }

    // 添加或更新员工
    @Action(value = "save", results = {
        @Result(name = "success", type = "json"),
        @Result(name = "error", type = "json")
    })
    public String save() {
        try {
            employeeService.saveOrUpdate(employee);
            addActionMessage("Employee saved successfully");
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Failed to save employee");
            return ERROR;
        }
    }

    // 删除员工
    @Action(value = "delete", results = {
        @Result(name = "success", type = "json"),
        @Result(name = "error", type = "json")
    })
    public String delete(HttpServletRequest request) {
        try {
            String employeeId = request.getParameter("id");
            employeeService.delete(employeeId);
            addActionMessage("Employee deleted successfully");
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Failed to delete employee");
            return ERROR;
        }
    }

    // Getter和Setter
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
