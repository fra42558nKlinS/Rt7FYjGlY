// 代码生成时间: 2025-08-28 05:49:40
package com.example.hashcalculator;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.Map;

/**
 * Struts2 Action class for hash calculation tool.
 * This class handles the request to calculate hash values for given input strings.
 */
public class HashCalculatorAction extends ActionSupport {

    /**
     * The input string to calculate the hash for.
     */
    private String input;

    /**
     * The calculated hash result.
     */
    private String hashResult;

    /**
     * Setter for the input string.
     * @param input The input string to calculate the hash for.
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * Getter for the calculated hash result.
     * @return The calculated hash result.
     */
    public String getHashResult() {
        return hashResult;
    }

    /**
     * The execute method to calculate the hash.
     * @return The Struts2 action result.
     */
    public String execute() {
        try {
            // Calculate MD5 hash of the input string
            hashResult = DigestUtils.md5Hex(input);
        } catch (Exception e) {
            // Handle any exceptions that occur during hash calculation
            addActionError("Error calculating hash: " + e.getMessage());
            return ERROR;
        }

        return SUCCESS;
    }

    // Additional hash algorithms can be added here as needed
    // For example, a method to calculate SHA-256 hash
    // private String calculateSHA256(String input) {
    //     return DigestUtils.sha256Hex(input);
    // }
}
