// 代码生成时间: 2025-08-04 15:23:45
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Action class for image resizing.
 * This class handles the upload of image files and resizing them to specified dimensions.
 */
@Results({
    @Result(name = "success", location = "/success.jsp"),
    @Result(name = "input", location = "/index.jsp")
})
public class ImageResizerAction extends ActionSupport {

    private List<FileItem> images;
    private String width;
    private String height;

    public String execute() {
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            for (FileItem item : images) {
                if (!item.isFormField()) {
                    String fileName = new File(item.getName()).getName();
                    String extension = FilenameUtils.getExtension(fileName);
                    File uploadedFile = new File("uploaded/" + fileName);
                    item.write(uploadedFile);

                    BufferedImage originalImage = ImageIO.read(uploadedFile);
                    int targetWidth = Integer.parseInt(width);
                    int targetHeight = Integer.parseInt(height);

                    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
                    resizedImage.getGraphics().drawImage(originalImage.getScaledInstance(targetWidth, targetHeight, BufferedImage.SCALE_SMOOTH), 0, 0, null);

                    // Save the resized image
                    ImageIO.write(resizedImage, extension, new File("resized/" + fileName));

                    // Delete the original image
                    uploadedFile.delete();
                }
            }
        } catch (Exception e) {
            addActionError("Error resizing images: " + e.getMessage());
            return INPUT;
        }
        return SUCCESS;
    }

    // Getters and Setters
    public List<FileItem> getImages() {
        return images;
    }

    public void setImages(List<FileItem> images) {
        this.images = images;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}