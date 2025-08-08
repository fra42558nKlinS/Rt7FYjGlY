// 代码生成时间: 2025-08-08 21:35:12
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.util.StrutsUtil;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.dispatcher.multipart.MultiPartStack;
import org.apache.struts2.dispatcher.multipart.JakartaMultiPartRequestWrapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 使用Struts框架和JAVA实现的文件压缩工具
@ParentPackage("default")
@Namespace("/unzip")
@ResultPath("/WEB-INF/views")
@Results({
    @Result(name = "success", location = "success.jsp"),
    @Result(name = "input", location = "input.jsp\)
})
public class UnzipTool extends StrutsUtil {
    private File uploadedFile;
    private String fileName;
    private String filePath;
    private String extractPath;

    public File getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(File uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        return filePath;
    }

    public String getExtractPath() {
        return extractPath;
    }

    public void setExtractPath(String extractPath) {
        this.extractPath = extractPath;
    }

    // 提交文件并解压
    @SkipValidation
    @Action(value = "uploadAndUnzip", results = {
        @Result(name = "success", type = "json")
    })
    public String uploadAndUnzip() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        MultiPartRequestWrapper multiPartRequest = (MultiPartRequestWrapper) request;
        MultiPartStack multiPartStack = new MultiPartStack();
        multiPartStack.setBoundary((JakartaMultiPartRequestWrapper) multiPartRequest);
        String fileName = multiPartRequest.getOriginalFileName("file");
        String filePath = multiPartRequest.getRealPath("/") + "uploadedFiles/";
        String extractPath = multiPartRequest.getRealPath("/") + "extractedFiles/";

        try {
            // 将文件保存到指定路径
            File destinationFile = new File(filePath + fileName);
            try (InputStream inputStream = multiPartRequest.getFile("file").getInputStream();
                 OutputStream outputStream = new FileOutputStream(destinationFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            // 解压文件
            unzipFile(filePath + fileName, extractPath);

            // 返回成功状态
            response.setContentType("application/json");
            return SUCCESS;

        } catch (IOException e) {
            // 处理错误并返回错误状态
            e.printStackTrace();
            return ERROR;
        }
    }

    // 解压ZIP文件
    private void unzipFile(String zipFilePath, String extractPath) throws IOException {
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipIn.getNextEntry();
            // 遍历ZIP文件中的条目
            while (entry != null) {
                String filePath = extractPath + entry.getName();
                if (!entry.isDirectory()) {
                    // 如果是文件，则解压
                    extractFile(zipIn, filePath);
                } else {
                    // 如果是目录，则创建目录
                    new File(filePath).mkdirs();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
    }

    // 从ZIP输入流中提取文件
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }
}