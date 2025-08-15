// 代码生成时间: 2025-08-15 10:42:34
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

// Action类用于处理哈希值计算工具的业务逻辑
public class HashValueCalculator extends ActionSupport {

    // 属性，用于接收用户输入的字符串
    private String input;

    // 属性，用于存储生成的哈希值
    private String hashValue;

    // 构造函数
    public HashValueCalculator() {
        super();
    }

    // 设置输入字符串
    public void setInput(String input) {
        this.input = input;
    }

    // 获取输入字符串
    public String getInput() {
        return input;
    }

    // 设置哈希值
    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }

    // 获取哈希值
    public String getHashValue() {
        return hashValue;
    }

    // 执行哈希值计算的方法
    public String calculateHash() {
        // 检查输入是否为空
        if (input == null || input.isEmpty()) {
            addActionError("Input string cannot be empty.");
            return INPUT;
        }

        try {
            // 使用SHA-256算法生成哈希值
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes("UTF-8"));
            // 转换字节数组为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            hashValue = hexString.toString();
            return SUCCESS;
        } catch (NoSuchAlgorithmException e) {
            // 处理算法不支持异常
            addActionError("Hash algorithm not supported: " + e.getMessage());
            return ERROR;
        } catch (UnsupportedEncodingException e) {
            // 处理编码不支持异常
            addActionError("Encoding not supported: " + e.getMessage());
            return ERROR;
        }
    }

    // 用于Struts2的execute方法
    @Override
    public String execute() throws Exception {
        return calculateHash();
    }
}
