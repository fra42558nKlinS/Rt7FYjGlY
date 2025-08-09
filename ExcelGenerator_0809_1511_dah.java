// 代码生成时间: 2025-08-09 15:11:04
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.*;

public class ExcelGenerator {
    // 主方法，用于测试ExcelGenerator类
    public static void main(String[] args) {
        try {
            // 创建Excel工作簿
            Workbook workbook = new XSSFWorkbook();
            // 创建一个工作表sheet
            Sheet sheet = workbook.createSheet("Sheet1");

            // 向sheet中添加数据
            populateSheet(sheet);

            // 将工作簿写入文件
            FileOutputStream outputStream = new FileOutputStream("GeneratedExcel.xlsx");
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

            System.out.println("Excel文件已生成: GeneratedExcel.xlsx");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 向工作表添加数据
    private static void populateSheet(Sheet sheet) {
        // 创建行和单元格样式
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setWrapText(true);

        // 创建标题行
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(20);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Excel自动生成器");
        titleCell.setCellStyle(cellStyle);

        // 创建数据行
        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue("数据1");
        dataRow.createCell(1).setCellValue("数据2");
        // 可以根据需要添加更多数据

        // 设置列宽
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
    }
}