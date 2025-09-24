// 代码生成时间: 2025-09-24 08:57:59
package com.example.random;

/**
 * RandomNumberGenerator.java
 * 
 * @author Your Name
 * @version 1.0
 * @since 2023-04-25
 *
 * This class provides a random number generator using Java's built-in Random class.
 * It is designed to be a simple, easy-to-understand example of using Struts in a Java application.
 */
public class RandomNumberGenerator {

    private static final int DEFAULT_MIN = 1; // Default minimum value
    private static final int DEFAULT_MAX = 100; // Default maximum value
    private final Random random; // Instance of Random class

    /**
     * Constructor for RandomNumberGenerator.
     */
    public RandomNumberGenerator() {
        this.random = new Random(); // Initialize the Random object
    }

    /**
     * Generates a random number within a specified range.
     * 
     * @param min Minimum value of the range (inclusive)
     * @param max Maximum value of the range (inclusive)
     * @return A random integer between min and max
     */
    public int generateRandomNumber(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum value cannot be greater than maximum value.");
        }
        return random.nextInt((max - min) + 1) + min; // Generate random number
    }

    /**
     * Generates a random number with default range 1 to 100.
     * 
     * @return A random integer between 1 and 100
     */
    public int generateRandomNumber() {
        return generateRandomNumber(DEFAULT_MIN, DEFAULT_MAX); // Use default range
    }

    // Main method for testing the RandomNumberGenerator class
    public static void main(String[] args) {
        RandomNumberGenerator rng = new RandomNumberGenerator();
        try {
            int randomNumber = rng.generateRandomNumber();
            System.out.println("Generated random number: " + randomNumber);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
