// 代码生成时间: 2025-08-04 04:27:16
package com.example.math.service;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;

public class MathOperationsService extends ActionSupport {

    // The following operations are supported:
    // add, subtract, multiply, divide
    private double add(double a, double b) {
        // Adds two numbers
        return a + b;
    }

    private double subtract(double a, double b) {
        // Subtracts b from a
        return a - b;
    }

    private double multiply(double a, double b) {
        // Multiplies two numbers
        return a * b;
    }

    private double divide(double a, double b) {
        // Divides a by b. Error handling is necessary to avoid division by zero.
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }

    public String performOperation(Map<String, String> parameters) {
        try {
            double num1 = Double.parseDouble(parameters.get("num1"));
            double num2 = Double.parseDouble(parameters.get("num2"));
            String operation = parameters.get("operation");

            switch (operation) {
                case "add":
                    return String.valueOf(add(num1, num2));
                case "subtract":
                    return String.valueOf(subtract(num1, num2));
                case "multiply":
                    return String.valueOf(multiply(num1, num2));
                case "divide":
                    return String.valueOf(divide(num1, num2));
                default:
                    return "Invalid operation";
            }
        } catch (NumberFormatException e) {
            return "Invalid input. Please enter numeric values.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}
