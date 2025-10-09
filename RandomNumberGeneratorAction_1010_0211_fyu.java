// 代码生成时间: 2025-10-10 02:11:23
package com.example.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import java.util.Random;

/**
 * Action class for generating random numbers.
 */
@Results({
    @Result(name = "success", type = "json")
})
public class RandomNumberGeneratorAction extends ActionSupport {

    private String randomNumber;
    private int lowerBound = 1;
    private int upperBound = 100;

    /**
     * Generate a random number within the specified range.
     * 
     * @return String - the generated random number.
     */
    @Action(value = "/generateRandomNumber", results = {
        @Result(name = "success", params = {"location", "/WEB-INF/jsp/generateRandomNumber.jsp", "contentType", "application/json"})
    })
    public String generateRandomNumber() {
        try {
            Random random = new Random();
            this.randomNumber = String.valueOf(random.nextInt(upperBound - lowerBound + 1) + lowerBound);
            // Adding the generated random number to the value stack
            addActionMessage("Random number generated: " + this.randomNumber);
        } catch (Exception e) {
            // Handling any potential exceptions
            addActionError("Error occurred while generating random number: " + e.getMessage());
            return ERROR;
        }
        return SUCCESS;
    }

    // Getters and setters for the properties
    public String getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(String randomNumber) {
        this.randomNumber = randomNumber;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }
}
