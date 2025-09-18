// 代码生成时间: 2025-09-18 16:05:37
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * WebContentCrawler.java - A Struts action class to fetch web content.
 *
 * @author Your Name
 * @version 1.0
 */
public class WebContentCrawler extends DispatchAction {

    /**
     * Default Action Method - Fetches and displays web content.
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws IOException
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String url = request.getParameter("url");
        if (url == null || url.trim().isEmpty()) {
            // Handle the case where no URL is provided.
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "URL parameter is missing.");
            return null;
        }

        try {
            Document doc = Jsoup.connect(url).get();
            request.setAttribute("title", doc.title());
            request.setAttribute("content", extractContent(doc));
        } catch (IOException e) {
            // Handle connection errors or other IO related exceptions.
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching content: " + e.getMessage());
            return null;
        }

        return mapping.findForward("success");
    }

    /**
     * Extracts the main content from the document.
     *
     * @param doc The Jsoup document object.
     * @return A string containing the main content.
     */
    private String extractContent(Document doc) {
        Elements contentDivs = doc.select("div.content");
        if (!contentDivs.isEmpty()) {
            return contentDivs.get(0).html();
        }
        return "";
    }
}
