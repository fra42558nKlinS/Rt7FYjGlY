// 代码生成时间: 2025-09-06 18:55:19
 * It follows Java best practices for code clarity, error handling, and maintainability.
 */
package com.example.memory;

import org.apache.struts2.ServletActionContext;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.HashMap;
import java.util.Map;

public class MemoryUsageAnalyzer {

    private MemoryMXBean memoryMXBean;

    /**
     * Constructor for MemoryUsageAnalyzer.
     */
    public MemoryUsageAnalyzer() {
        this.memoryMXBean = ManagementFactory.getMemoryMXBean();
    }

    /**
     * Analyzes the memory usage and provides a map of memory pool names to their respective usage percentages.
     * @return A map with memory pool names as keys and their usage percentages as values.
     */
    public Map<String, Double> analyzeMemoryUsage() {
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        Map<String, Double> memoryUsageMap = new HashMap<>();

        // Calculate and add heap memory usage percentage
        memoryUsageMap.put("Heap Memory Usage", calculateMemoryUsage(heapMemoryUsage));

        // Calculate and add non-heap memory usage percentage
        memoryUsageMap.put("Non-Heap Memory Usage", calculateMemoryUsage(nonHeapMemoryUsage));

        return memoryUsageMap;
    }

    /**
     * Calculates the memory usage percentage based on the initial and used memory.
     * @param memoryUsage The memory usage object.
     * @return The memory usage percentage as a double value.
     */
    private double calculateMemoryUsage(MemoryUsage memoryUsage) {
        long maxMemory = memoryUsage.getMax();
        long usedMemory = memoryUsage.getUsed();
        if (maxMemory == 0) {
            // Avoid division by zero
            return 0;
        }
        return (double) usedMemory / (double) maxMemory * 100;
    }

    /**
     * Main method for testing the memory usage analysis.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        MemoryUsageAnalyzer analyzer = new MemoryUsageAnalyzer();
        Map<String, Double> memoryUsage = analyzer.analyzeMemoryUsage();

        for (Map.Entry<String, Double> entry : memoryUsage.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + "%");
        }
    }
}
