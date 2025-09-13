// 代码生成时间: 2025-09-13 12:04:37
package com.example.util;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * ApiResponseFormatter is a utility class for formatting API responses.
 * This class provides methods to create standardized API responses with
 * error handling and data encapsulation.
 */
@Results({
    @Result(name = "success", type = "json"),
    @Result(name = "error", type = "json\)
})
public class ApiResponseFormatter extends ActionSupport implements ServletRequestAware {

    private static final Logger logger = LoggerFactory.getLogger(ApiResponseFormatter.class);
    private HttpServletRequest request;

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Formats a successful API response with a message and data.
     *
     * @param message The success message to be included in the response.
     * @param data The data to be encapsulated in the response.
     * @return A formatted JSON response.
     */
    public String formatSuccessResponse(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);
        if (data != null) {
            response.put("data", data);
        }
        return toJson(response);
    }

    /**
     * Formats an error API response with an error message and error code.
     *
     * @param message The error message to be included in the response.
     * @param errorCode The error code associated with the error message.
     * @return A formatted JSON response.
     */
    public String formatErrorResponse(String message, int errorCode) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", message);
        response.put("errorCode", errorCode);
        return toJson(response);
    }

    /**
     * Converts a Map to a JSON string.
     * This method uses the request's JSON library to perform the conversion.
     *
     * @param map The map to be converted to JSON.
     * @return A JSON string representation of the map.
     */
    private String toJson(Map<String, Object> map) {
        try {
            return getJsonLibrary().serialize(map);
        }
        catch (Exception e) {
            logger.error("Error serializing response to JSON", e);
            return formatErrorResponse("Internal Server Error", 500);
        }
    }

    /**
     * Obtains the JSON library from the request.
     * This method should be implemented based on the specific JSON library being used.
     *
     * @return The JSON library instance.
     */
    private Object getJsonLibrary() {
        // Implementation depends on the JSON library used, e.g., Jackson, Gson, etc.
        // Return the JSON library instance here.
        return null;
    }
}
