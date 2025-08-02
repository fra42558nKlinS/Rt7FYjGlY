// 代码生成时间: 2025-08-02 20:38:51
package com.example.batchrename;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Struts Action class for batch file renaming.
 */
@Namespace("/batchrename")
@ParentPackage("default")
public class BatchFileRenameAction extends ActionSupport {

    private String sourceDirectory;
    private String newFilePrefix;
    private List<String> fileNames;
    private String resultMessage;

    // Getter and setter for sourceDirectory
    public String getSourceDirectory() {
        return sourceDirectory;
    }

    public void setSourceDirectory(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    // Getter and setter for newFilePrefix
    public String getNewFilePrefix() {
        return newFilePrefix;
    }

    public void setNewFilePrefix(String newFilePrefix) {
        this.newFilePrefix = newFilePrefix;
    }

    // Getter for fileNames
    public List<String> getFileNames() {
        return fileNames;
    }

    // Getter for resultMessage
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * Rename files in the specified directory with a new prefix.
     * @return A string indicating the result of the operation.
     */
    @Action(value = "renameFiles", results = {@Result(name = SUCCESS, location = "/batchrename/success.jsp")})
    public String renameFiles() {
        fileNames = new ArrayList<>();
        File dir = new File(sourceDirectory);

        // Check if the directory exists and is a directory
        if (!dir.exists() || !dir.isDirectory()) {
            resultMessage = "The specified directory does not exist or is not a directory.";
            return ERROR;
        }

        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            resultMessage = "No files found in the specified directory.";
            return ERROR;
        }

        int fileCount = 0;
        for (File file : files) {
            if (file.isFile()) {
                String newFileName = newFilePrefix + fileCount + "." + getFileExtension(file.getName());
                File newFile = new File(dir, newFileName);

                try {
                    if (file.renameTo(newFile)) {
                        fileNames.add(newFileName);
                        fileCount++;
                    } else {
                        resultMessage = "Failed to rename file: " + file.getName();
                        return ERROR;
                    }
                } catch (SecurityException e) {
                    resultMessage = "Access denied when renaming file: " + file.getName();
                    return ERROR;
                }
            }
        }

        resultMessage = fileCount + " files have been renamed successfully.";
        return SUCCESS;
    }

    /**
     * Extract the file extension from a file name.
     * @param fileName The name of the file.
     * @return The file extension or an empty string if no extension is found.
     */
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }
}
