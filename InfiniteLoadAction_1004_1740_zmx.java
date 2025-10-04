// 代码生成时间: 2025-10-04 17:40:54
package com.example.infiniteload;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import java.util.ArrayList;
import java.util.List;

@Namespace("/infiniteLoad")
@Result(name = "success", type = "json")
public class InfiniteLoadAction extends ActionSupport {

    // Pagination parameters
    private int pageNumber = 1;
    private int pageSize = 10;

    // Data list to simulate database records
    private List<String> records;

    // Constructor
    public InfiniteLoadAction() {
        records = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            records.add("Record " + i);
        }
    }

    @Action("loadData")
    public String loadData() {
        try {
            // Calculate the start index based on the page number and page size
            int startIndex = (pageNumber - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, records.size());

            // Get the portion of records that should be displayed on the current page
            List<String> pageRecords = records.subList(startIndex, endIndex);

            // Set the result for JSON rendering
            addActionMessage("Loaded page: " + pageNumber);
            addActionError("Total records: " + records.size());
            
            // Return success to render the JSON result
            return SUCCESS;
        } catch (Exception e) {
            // Handle any exceptions that may occur
            addActionError("Error loading data: " + e.getMessage());
            return ERROR;
        }
    }

    // Getters and setters
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
