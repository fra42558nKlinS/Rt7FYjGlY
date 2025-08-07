// 代码生成时间: 2025-08-07 14:26:48
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelAutoGenerator {

    /**
     * Generates an Excel file with the given data and sends it as a response to the client.
     * 
     * @param data The data to be written to the Excel file.
     * @param sheetName The name of the sheet in the Excel file.
     * @throws IOException If an I/O error occurs while writing the file.
     */
    public static void generateAndSendExcel(List<List<Object>> data, String sheetName) throws IOException {
        // Create a new Excel workbook
        Workbook workbook = new XSSFWorkbook();

        // Create a new sheet
        Sheet sheet = workbook.createSheet(sheetName);

        // Create a header row
        Row headerRow = sheet.createRow(0);
        int headerColumn = 0;

        // Assuming the first row of data is the header
        if (data != null && !data.isEmpty()) {
            for (Object header : data.get(0)) {
                Cell cell = headerRow.createCell(headerColumn++);
                cell.setCellValue(header.toString());
            }
        }

        // Write data rows
        for (int i = (data.get(0).isEmpty() ? 0 : 1); i < data.size(); i++) {
            Row row = sheet.createRow(i);
            List<Object> rowData = data.get(i);
            for (int j = 0; j < rowData.size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(rowData.get(j).toString());
            }
        }

        // Write the response to the client
        writeExcelToClient(workbook);
    }

    /**
     * Writes the Excel workbook to the client's response as an Excel file.
     * 
     * @param workbook The workbook to be written.
     * @throws IOException If an I/O error occurs while writing the file.
     */
    private static void writeExcelToClient(Workbook workbook) throws IOException {
        try (OutputStream out = ServletActionContext.getResponse().getOutputStream();
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            // Write the workbook to a byte array output stream
            workbook.write(bos);

            // Set the content type and attachment header
            ServletActionContext.getResponse().setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            ServletActionContext.getResponse().setHeader("Content-Disposition", "attachment;filename=GeneratedExcel.xlsx");

            // Write the byte array to the client's response
            out.write(bos.toByteArray());
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        try {
            List<List<Object>> data = new ArrayList<>();
            data.add(List.of("Name", "Age", "City"));
            data.add(List.of("John Doe", 30, "New York"));
            data.add(List.of("Jane Doe", 25, "Los Angeles"));

            generateAndSendExcel(data, "People");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}