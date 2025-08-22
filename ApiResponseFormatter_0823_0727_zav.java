// 代码生成时间: 2025-08-23 07:27:57
import org.apache.struts2.json.JSONUtil;
import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ApiResponseFormatter extends ActionSupport {
    // 定义响应的属性
    private String message;
    private boolean success;
    private Object data;

    // 构造函数
    public ApiResponseFormatter() {
    }

    // 设置消息
    public void setMessage(String message) {
        this.message = message;
    }

    // 设置是否成功
    public void setSuccess(boolean success) {
        this.success = success;
    }

    // 设置数据
    public void setData(Object data) {
        this.data = data;
    }

    // 格式化响应为JSON并返回
    public String formatResponse() {
        // 创建JSON对象
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("message", message);
        jsonResponse.put("success", success);

        // 如果数据不为空，则添加到响应中
        if (data != null) {
            jsonResponse.put("data", data);
        }

        // 设置响应类型为JSON
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json");

        // 写入响应
        try (PrintWriter writer = response.getWriter()) {
            writer.print(jsonResponse.toString());
        } catch (Exception e) {
            // 错误处理
            addActionError("Error formatting response: " + e.getMessage());
            return ERROR;
        }

        return SUCCESS;
    }

    // 重写execute方法以使用formatResponse
    @Override
    public String execute() {
        return formatResponse();
    }
}