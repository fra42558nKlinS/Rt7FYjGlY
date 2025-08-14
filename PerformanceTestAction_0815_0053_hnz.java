// 代码生成时间: 2025-08-15 00:53:38
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.dispatcher.SessionMap;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * PerformanceTestAction class is designed to perform performance testing on the system.
 * It sends concurrent requests to a specified URL and measures the response time.
 */
@Component
@Scope("prototype")
@Namespace("/test")
@Results({
    @Result(name = StrutsStatics.STRUTS_ACTION_MESSAGE, location = "messages.jsp"),
    @Result(name = "success", location = "success.jsp")
})
public class PerformanceTestAction extends ActionSupport {

    private String url;
    private int numberOfRequests;
    private long responseTime;

    @Resource
    private SessionMap sessionMap; // SessionMap to store execution metrics

    /**
     * Perform performance test method.
     * @return String, the result of the action.
     */
    @Action(value = "performTest", results = {
        @Result(name = "input", location = "test.jsp")
    })
    public String performTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(10); // ThreadPool to handle concurrent requests
        try {
            for (int i = 0; i < numberOfRequests; i++) {
                executorService.submit(new TestTask(url)); // Submit tasks to the ExecutorService
            }
            executorService.shutdown(); // Initiate a shutdown in which previously submitted tasks are executed, but no new tasks will be accepted.
            executorService.awaitTermination(60, TimeUnit.SECONDS); // Wait for all tasks to finish execution.
            // Calculate average response time
            responseTime = (long) sessionMap.get("totalResponseTime") / numberOfRequests;
        } catch (InterruptedException e) {
            addActionError("Error performing performance test: " + e.getMessage());
            return ERROR;
        } finally {
            executorService.shutdownNow(); // Attempt to stop all actively executing tasks and halt the ExecutorService.
        }
        return SUCCESS;
    }

    /**
     * TestTask class which represents a task to be executed.
     */
    private class TestTask implements Runnable {
        private String testUrl;
        public TestTask(String url) {
            this.testUrl = url;
        }

        public void run() {
            try {
                // Simulate a request to the specified URL (implementation not shown)
                long startTime = System.currentTimeMillis();
                // sendRequest(testUrl);
                long endTime = System.currentTimeMillis();
                long requestTime = endTime - startTime;
                synchronized (sessionMap) {
                    long totalResponseTime = (Long) sessionMap.get("totalResponseTime");
                    if (totalResponseTime == null) {
                        totalResponseTime = 0;
                    }
                    sessionMap.put("totalResponseTime", totalResponseTime + requestTime);
                }
            } catch (Exception e) {
                addActionError("Error sending request: " + e.getMessage());
            }
        }
    }

    // Getters and setters for the fields
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumberOfRequests() {
        return numberOfRequests;
    }

    public void setNumberOfRequests(int numberOfRequests) {
        this.numberOfRequests = numberOfRequests;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }
}
