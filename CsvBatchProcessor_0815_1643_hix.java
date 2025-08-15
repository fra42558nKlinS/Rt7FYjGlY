// 代码生成时间: 2025-08-15 16:43:10
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV文件批量处理器
 */
@ParentPackage("default")
@Namespace("/csvProcessor")
@Results({"success": "success.jsp"})
public class CsvBatchProcessor extends BaseAction implements SessionAware, ServletRequestAware {

    private List<String> csvContentList = new ArrayList<>();
    private String sessionKey = "csvContentList";
    private MultipartFile[] csvFiles;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    /**
     * 处理CSV文件上传
     *
     * @return
     * @throws IOException
     */
    @Action(value = "process", results = {
            @Result(name = "input", location = "input.jsp"),
            @Result(location = "success.jsp")
    })
    public String process() throws IOException {
        if (csvFiles != null && csvFiles.length > 0) {
            for (MultipartFile file : csvFiles) {
                try {
                    if (!file.isEmpty()) {
                        List<String> contentList = readCsvFile(file);
                        csvContentList.addAll(contentList);
                    }
                } catch (IOException e) {
                    addActionError("Error processing file: " + file.getOriginalFilename());
                    return "input";
                }
            }
            session.setAttribute(sessionKey, csvContentList);
            return "success";
        } else {
            addActionError("No CSV files were uploaded.");
            return "input";
        }
    }

    /**
     * 读取CSV文件内容
     *
     * @param file
     * @return
     * @throws IOException
     */
    private List<String> readCsvFile(MultipartFile file) throws IOException {
        List<String> contentList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentList.add(line);
            }
        }
        return contentList;
    }

    // Getters and Setters
    public MultipartFile[] getCsvFiles() {
        return csvFiles;
    }

    public void setCsvFiles(MultipartFile[] csvFiles) {
        this.csvFiles = csvFiles;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setHttpServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public void setSession(HttpSession session) {
        this.session = session;
    }
}
