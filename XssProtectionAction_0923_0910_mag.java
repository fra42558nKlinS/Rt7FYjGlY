// 代码生成时间: 2025-09-23 09:10:02
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * This action class is designed to protect against XSS (Cross-Site Scripting) attacks.
 * It filters user input to prevent malicious scripts from being injected into the application.
 */
public class XssProtectionAction extends ActionSupport {

    /**
     * The input string provided by the user.
     */
    private String userInput;

    /**
     * Setter for userInput, which applies the XSS protection.
     *
     * @param userInput The input string from the user.
     */
    public void setUserInput(String userInput) {
        this.userInput = StringEscapeUtils.escapeHtml4(userInput);
    }

    /**
     * Getter for userInput.
     *
     * @return The sanitized user input.
     */
    public String getUserInput() {
        return userInput;
    }

    /**
     * The execute method is called when the action is invoked.
     * It handles the request and response, applying XSS protection to the user input.
     *
     * @return The result string indicating the action's status.
     */
    @Override
    public String execute() {
        try {
            // Get the HttpServletRequest from the ServletActionContext
            HttpServletRequest request = ServletActionContext.getRequest();

            // Retrieve the user input from the request
            String userInput = request.getParameter("userInput");

            // Set the user input, which triggers the XSS protection
            setUserInput(userInput);

            // Add the sanitized input back to the request for use in the view
            request.setAttribute("sanitizedInput", getUserInput());

            // Proceed with processing the request
            return SUCCESS;
        } catch (Exception e) {
            // Handle any errors that occur during processing
            addActionError("An error occurred while processing your request: " + e.getMessage());
            return ERROR;
        }
    }
}
