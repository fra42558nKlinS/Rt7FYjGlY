// 代码生成时间: 2025-08-26 12:05:41
package com.example.struts.actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;

public class ExcelGeneratorAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    /**
     * Generate an Excel file with sample data and send it as a response.
     *
     * @return SUCCESS if the file is generated successfully, otherwise ERROR.
     */
    public String generateExcel() {
        try {
            // Create a new Excel workbook
            Workbook workbook = new XSSFWorkbook();

            // Add a sample sheet to the workbook
            workbook.createSheet("Sample Sheet");

            // Set the content type and headers for the response
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename="sample-excel-file-" + new Date().getTime() + ".xlsx"");

            // Write the workbook to the response output stream
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();

            // Clear the session to remove the workbook from memory
            ServletActionContext.getRequest().getSession().invalidate();

            return SUCCESS;
        } catch (IOException e) {
            // Handle exceptions and set the appropriate error message
            this.addActionError("Error occurred while generating the Excel file: " + e.getMessage());
            return ERROR;
        }
    }
}
