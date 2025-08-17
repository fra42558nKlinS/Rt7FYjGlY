// 代码生成时间: 2025-08-17 23:19:19
package com.example.struts2.preventsqlinjection;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Action class to prevent SQL injection using prepared statements and validation.
 * This class demonstrates how to use Struts2 framework with JDBC to safely execute SQL queries.
 */
public class PreventSQLInjectionAction extends ActionSupport implements SessionAware {

    private static final Logger logger = LoggerFactory.getLogger(PreventSQLInjectionAction.class);

    private List<String> userList = new ArrayList<>();
    private DataSource dataSource;
    private String sessionUsername;

    /**
     * Method to set the DataSource.
     * @param dataSource The data source to be used for database operations.
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Method to get the user list from the database.
     * @return A list of user names.
     */
    @Action(value = "/getUserList", results = {"json": "jsonResult"})
    public String getUserList() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT username FROM users WHERE username = ?")) {

            // Assuming username is extracted from the session
            stmt.setString(1, sessionUsername);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                userList.add(rs.getString("username"));
            }

        } catch (SQLException e) {
            logger.error("SQL Error occurred while fetching user list.", e);
            addActionError("An error occurred while fetching user data.");
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * Method to set the session username.
     * @param username The username from the session.
     */
    public void setSessionUsername(String username) {
        this.sessionUsername = username;
    }

    /**
     * Method to get the user list.
     * @return A list of user names.
     */
    public List<String> getUserList() {
        return userList;
    }

    // Additional methods, configuration, and error handling can be added here.
}
