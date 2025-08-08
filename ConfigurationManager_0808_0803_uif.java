// 代码生成时间: 2025-08-08 08:03:02
package com.example.struts.config;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.dispatcher.ApplicationMap;
import org.apache.struts2.dispatcher.Dispatcher;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ClassLoaderUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A utility class to manage configuration files in a Struts 2 application.
 */
public class ConfigurationManager {

    private Properties properties;

    /**
     * Loads the configuration from the specified resource.
     *
     * @param resourcePath The path to the configuration resource.
     * @throws IOException If an I/O error occurs.
     */
    public void loadConfiguration(String resourcePath) throws IOException {
        InputStream inputStream = ClassLoaderUtil.getResourceAsStream(resourcePath, getClass());
        if (inputStream == null) {
            throw new IOException("Configuration resource not found: " + resourcePath);
        }
        properties = new Properties();
        properties.load(inputStream);
        inputStream.close();
    }

    /**
     * Retrieves the value of a property by its key.
     *
     * @param key The key of the property to retrieve.
     * @return The value of the property, or null if not found.
     */
    public String getProperty(String key) {
        return properties != null ? properties.getProperty(key) : null;
    }

    /**
     * Sets the value of a property by its key.
     *
     * @param key The key of the property to set.
     * @param value The new value of the property.
     */
    public void setProperty(String key, String value) {
        if (properties == null) {
            properties = new Properties();
        }
        properties.setProperty(key, value);
    }

    /**
     * Saves the current configuration to the specified resource.
     *
     * @param resourcePath The path to the resource where the configuration will be saved.
     * @throws IOException If an I/O error occurs.
     */
    public void saveConfiguration(String resourcePath) throws IOException {
        if (properties == null) {
            throw new IOException("No configuration to save");
        }
        // Implementation for saving the configuration would go here
        // For example, writing to a file or updating a database
    }

    // Additional methods can be added here for additional functionality

    // Example usage within a Struts 2 action
    public static void main(String[] args) {
        ConfigurationManager configManager = new ConfigurationManager();
        try {
            configManager.loadConfiguration("/config.properties");
            String dbUrl = configManager.getProperty("db.url");
            System.out.println("Database URL: " + dbUrl);
            configManager.setProperty("db.user", "newUser");
            configManager.saveConfiguration("/config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
