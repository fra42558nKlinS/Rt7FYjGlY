// 代码生成时间: 2025-08-24 01:56:06
package com.example.imageresizer;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is an implementation of Struts Action class for batch image resizing.
 * It handles the resizing of images based on user input and provides error handling.
 */
public class ImageResizer extends DispatchAction {

    /*
     * Method to resize images.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for any command forwarding.
     * @return The name of the forward to which control should be transferred.
     */
    public String resizeImages(ActionMapping mapping, ActionForm form) {
        ImageResizeForm resizeForm = (ImageResizeForm) form;

        File directory = new File(resizeForm.getDirectoryPath());
        int targetWidth = Integer.parseInt(resizeForm.getTargetWidth());
        int targetHeight = Integer.parseInt(resizeForm.getTargetHeight());

        // Check if the directory exists and is a directory
        if (!directory.exists() || !directory.isDirectory()) {
            addActionError(new ActionError("Invalid directory path"));
            return mapping.getInputForward();
        }

        List<File> imageFiles = getImageFiles(directory);

        for (File file : imageFiles) {
            try {
                BufferedImage originalImage = ImageIO.read(file);
                BufferedImage resizedImage = resizeImage(originalImage, targetWidth, targetHeight);

                // Save the resized image back to the same directory
                ImageIO.write(resizedImage, "png", new File(directory, file.getName()));
            } catch (IOException e) {
                addActionError(new ActionError("Error processing image: " + file.getName() + " - " + e.getMessage()));
            }
        }

        addActionMessage(new ActionMessage("All images have been resized successfully."));
        return mapping.findForward("success");
    }

    /*
     * Helper method to resize an image.
     * @param originalImage The original image to be resized.
     * @param targetWidth The target width for resizing.
     * @param targetHeight The target height for resizing.
     * @return The resized image.
     */
    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        // Implement your resizing logic here
        // For simplicity, using a simple scaling method
        java.awt.Graphics2D graphics2D = originalImage.createGraphics();
        graphics2D.drawImage(
            originalImage.getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_SMOOTH),
            0, 0, null
        );
        graphics2D.dispose();
        return graphics2D.getDeviceConfiguration().createCompatibleImage(
            targetWidth, targetHeight, java.awt.Transparency.TRANSLUCENT
        );
    }

    /*
     * Helper method to retrieve a list of image files from the directory.
     * @param directory The directory to search for image files.
     * @return A list of image files.
     */
    private List<File> getImageFiles(File directory) {
        List<File> fileList = new ArrayList<>();
        for (File file : directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().toLowerCase().endsWith(".png");
            }
        })) {
            fileList.add(file);
        }
        return fileList;
    }
}

/*
 * Supporting Form Bean
 */
public class ImageResizeForm extends ActionForm {
    private String directoryPath;
    private String targetWidth;
    private String targetHeight;

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getTargetWidth() {
        return targetWidth;
    }

    public void setTargetWidth(String targetWidth) {
        this.targetWidth = targetWidth;
    }

    public String getTargetHeight() {
        return targetHeight;
    }

    public void setTargetHeight(String targetHeight) {
        this.targetHeight = targetHeight;
    }
}