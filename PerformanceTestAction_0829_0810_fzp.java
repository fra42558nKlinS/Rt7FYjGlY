// 代码生成时间: 2025-08-29 08:10:54
package com.example.struts;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This action class is responsible for executing performance tests.
 */
@Results({
    @Result(name = "success", type = "json"),
    @Result(name = "input", type = "json\)
})
public class PerformanceTestAction extends ActionSupport {

    private static final int NUMBER_OF_THREADS = 100; // Number of threads to simulate concurrent requests
    private static final long DURATION = 60; // Duration of the test in seconds
    private int successfulRequests;
    private int failedRequests;
    private long startTime;
    private long endTime;

    // ExecutorService to manage threads
    private ExecutorService executor;

    public PerformanceTestAction() {
        executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    }

    @Action(value = "startTest", results = {
        @Result(name = "success", type = "json"),
        @Result(name = "input", type = "json")
    })
    public String startTest() {
        startTime = System.currentTimeMillis();
        endTime = startTime + TimeUnit.SECONDS.toMillis(DURATION);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            executor.submit(new TestRunnable());
        }

        return SUCCESS;
    }

    @Action(value = "stopTest", results = {
        @Result(name = "success", type = "json"),
        @Result(name = "input\, type = "json")
    })
    public String stopTest() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(DURATION, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            // Handle the exception
            addErrorMessage("Error while shutting down the executor: " + e.getMessage());
            executor.shutdownNow();
            return ERROR;
        }

        return SUCCESS;
    }

    // Getters and setters for successful and failed requests
    public int getSuccessfulRequests() {
        return successfulRequests;
    }

    public void setSuccessfulRequests(int successfulRequests) {
        this.successfulRequests = successfulRequests;
    }

    public int getFailedRequests() {
        return failedRequests;
    }

    public void setFailedRequests(int failedRequests) {
        this.failedRequests = failedRequests;
    }

    // Runnable to simulate a test request
    private class TestRunnable implements Runnable {
        public void run() {
            try {
                while (System.currentTimeMillis() < endTime) {
                    // Simulate a request
                    long startTimeRequest = System.currentTimeMillis();
                    // Simulate processing time (e.g., database call)
                    Thread.sleep(100);
                    long endTimeRequest = System.currentTimeMillis();

                    if (endTimeRequest - startTimeRequest < 200) {
                        successfulRequests++;
                    } else {
                        failedRequests++;
                    }
                }
            } catch (InterruptedException e) {
                // Handle the exception
                failedRequests++;
            }
        }
    }
}
