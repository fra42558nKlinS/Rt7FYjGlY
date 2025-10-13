// 代码生成时间: 2025-10-13 22:14:53
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import java.util.ArrayList;
import java.util.List;

/**
 * RFID标签管理Action类。
 * 提供添加、读取、更新和删除RFID标签的功能。
 */
public class RfidTagManagement extends Action {

    private List<String> tags = new ArrayList<>(); // 存储标签数据的列表

    /**
     * 添加RFID标签
     * @param mapping ActionMapping对象
     * @param form ActionForm对象
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return ActionForward对象
     */
    public ActionForward addTag(ActionMapping mapping, ActionForm form,
                                javax.servlet.http.HttpServletRequest request,
                                javax.servlet.http.HttpServletResponse response) {
        String newTag = request.getParameter("tag");
        if (newTag != null && !newTag.isEmpty()) {
            tags.add(newTag);
            request.setAttribute("message", "Tag added successfully");
        } else {
            ActionErrors errors = new ActionErrors();
            errors.add("error", new ActionError("error.tag.empty"));
            saveErrors(request, errors);
            return mapping.findForward("input");
        }
        return mapping.findForward("success");
    }

    /**
     * 读取RFID标签列表
     * @param mapping ActionMapping对象
     * @param form ActionForm对象
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return ActionForward对象
     */
    public ActionForward listTags(ActionMapping mapping, ActionForm form,
                                javax.servlet.http.HttpServletRequest request,
                                javax.servlet.http.HttpServletResponse response) {
        request.setAttribute("tags", tags);
        return mapping.findForward("success");
    }

    /**
     * 更新RFID标签
     * @param mapping ActionMapping对象
     * @param form ActionForm对象
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return ActionForward对象
     */
    public ActionForward updateTag(ActionMapping mapping, ActionForm form,
                                javax.servlet.http.HttpServletRequest request,
                                javax.servlet.http.HttpServletResponse response) {
        String tagId = request.getParameter("tagId");
        String updatedTag = request.getParameter("tag");
        if (tagId != null && updatedTag != null && !updatedTag.isEmpty()) {
            int index = Integer.parseInt(tagId);
            if (index >= 0 && index < tags.size()) {
                tags.set(index, updatedTag);
                request.setAttribute("message", "Tag updated successfully");
            } else {
                ActionErrors errors = new ActionErrors();
                errors.add("error", new ActionError("error.tag.index"));
                saveErrors(request, errors);
                return mapping.findForward("input");
            }
        } else {
            ActionErrors errors = new ActionErrors();
            errors.add("error", new ActionError("error.tag.empty"));
            saveErrors(request, errors);
            return mapping.findForward("input");
        }
        return mapping.findForward("success");
    }

    /**
     * 删除RFID标签
     * @param mapping ActionMapping对象
     * @param form ActionForm对象
     * @param request HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return ActionForward对象
     */
    public ActionForward deleteTag(ActionMapping mapping, ActionForm form,
                                javax.servlet.http.HttpServletRequest request,
                                javax.servlet.http.HttpServletResponse response) {
        String tagId = request.getParameter("tagId");
        if (tagId != null) {
            int index = Integer.parseInt(tagId);
            if (index >= 0 && index < tags.size()) {
                tags.remove(index);
                request.setAttribute("message", "Tag deleted successfully");
            } else {
                ActionErrors errors = new ActionErrors();
                errors.add("error", new ActionError("error.tag.index"));
                saveErrors(request, errors);
                return mapping.findForward("input");
            }
        } else {
            ActionErrors errors = new ActionErrors();
            errors.add("error", new ActionError("error.tag.id"));
            saveErrors(request, errors);
            return mapping.findForward("input");
        }
        return mapping.findForward("success");
    }
}