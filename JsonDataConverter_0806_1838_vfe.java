// 代码生成时间: 2025-08-06 18:38:02
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.json.JSONObject;
import org.json.JSONException;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import java.util.HashMap;
import java.util.Map;

/**
 * A Struts action class to handle JSON data conversion.
 */
@Results({"success" : "success.jsp", "input" : "input.jsp"})
public class JsonDataConverter extends ActionSupport {

    /**
     * Converts a JSON string into a map.
     * 
     * @param jsonString The JSON string to convert.
     * @return A map representing the JSON data.
     * @throws JSONException If the JSON is invalid.
     */
    private Map<String, Object> convertJsonToMap(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        Map<String, Object> map = new HashMap<>();
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = jsonObject.get(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * Executes the JSON conversion.
     * 
     * @return A string indicating the result of the operation.
     * @throws Exception If an error occurs during conversion.
     */
    @Result(name = "success", type = Result.Type.JSON)
    public String execute() throws Exception {
        String jsonString = getRequest().getParameter("jsonString");
        try {
            Map<String, Object> map = convertJsonToMap(jsonString);
            addActionMessage("This JSON was converted successfully");
            return SUCCESS;
        } catch (JSONException e) {
            addActionError("Invalid JSON: " + e.getMessage());
            return ERROR;
        }
    }
}
