// 代码生成时间: 2025-09-13 05:54:03
package com.example.imageresize;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.FileFilter;
import java.awt.image.ImageObserver;

public class ImageResizeBatchProcessor extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request, HttpServletResponse response) {
        String sourceFolder = request.getParameter("sourceFolder");
        String targetFolder = request.getParameter("targetFolder");
        int targetWidth = Integer.parseInt(request.getParameter("targetWidth"));
        int targetHeight = Integer.parseInt(request.getParameter("targetHeight"));

        // Check if the source and target folders are valid
        if (sourceFolder == null || targetFolder == null) {
            request.setAttribute("error", "Source and target folders must be specified.");
            return mapping.findForward("error");
        }

        File sourceDir = new File(sourceFolder);
        File targetDir = new File(targetFolder);

        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            request.setAttribute("error", "Source folder does not exist or is not a directory.");
            return mapping.findForward("error");
        }

        if (!targetDir.exists() && !targetDir.mkdirs()) {
            request.setAttribute("error", "Unable to create target folder.");
            return mapping.findForward("error");
        }

        try {
            resizeImages(sourceDir, targetDir, targetWidth, targetHeight);
            request.setAttribute("message", "Images resized successfully.");
        } catch (IOException e) {
            request.setAttribute("error", "Error resizing images: " + e.getMessage());
            return mapping.findForward("error");
        }

        return mapping.findForward("success");
    }

    /**
     * Resizes images in the given directory to the specified dimensions.
     *
     * @param sourceDir The directory containing the original images.
     * @param targetDir The directory where resized images will be saved.
     * @param width The target width of the resized images.
     * @param height The target height of the resized images.
     * @throws IOException If an I/O error occurs during processing.
     */
    private void resizeImages(File sourceDir, File targetDir, int width, int height) throws IOException {
        File[] files = sourceDir.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isFile() && (file.getName().endsWith(".jpg") ||
                                            file.getName().endsWith(".jpeg") ||
                                            file.getName().endsWith(".png"));
            }
        });

        if (files == null) {
            throw new IOException("Unable to list files in source directory.");
        }

        for (File file : files) {
            BufferedImage originalImage = ImageIO.read(file);
            if (originalImage == null) {
                throw new IOException("Failed to read image: " + file.getName());
            }

            BufferedImage resizedImage = resizeImage(originalImage, width, height);
            File targetFile = new File(targetDir, file.getName());
            ImageIO.write(resizedImage, getImageType(file.getName()), targetFile);
        }
    }

    /**
     * Resizes an image to the specified width and height.
     * Maintains the aspect ratio of the original image.
     *
     * @param originalImage The original image to resize.
     * @param width The target width of the resized image.
     * @param height The target height of the resized image.
     * @return A new BufferedImage instance with the resized image.
     */
    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        double aspectRatio = (double) originalImage.getWidth() / (double) originalImage.getHeight();
        int newWidth, newHeight;

        if (originalImage.getWidth() > originalImage.getHeight()) {
            newWidth = width;
            newHeight = (int) (width / aspectRatio);
        } else {
            newHeight = height;
            newWidth = (int) (height * aspectRatio);
        }

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D graphics2D = resizedImage.createGraphics();

        graphics2D.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        graphics2D.dispose();

        return resizedImage;
    }

    /**
     * Determines the image type from the file extension.
     *
     * @param filename The name of the file.
     * @return The image type as a String.
     */
    private String getImageType(String filename) {
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return "jpg";
        } else if (filename.endsWith(".png")) {
            return "png";
        } else {
            throw new IllegalArgumentException("Unsupported image format: " + filename);
        }
    }
}
