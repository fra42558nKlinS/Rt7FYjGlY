// 代码生成时间: 2025-08-25 19:48:33
package com.example.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import org.apache.struts2.ServletActionContext;

/**
 * A utility class for batch renaming files using the Struts framework.
 */
public class BatchFileRenamer {

    /**
     * Renames files in a specified directory based on a naming pattern.
     *
     * @param directoryPath The path to the directory containing the files to rename.
     * @param prefix The prefix to be added to each file name.
     * @throws IOException If an I/O error occurs.
     */
    public void renameFiles(String directoryPath, String prefix) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            // Log error and rethrow exception for better error handling in the Struts action
            throw new IOException("The specified directory does not exist or is not a directory.");
        }

        File[] files = directory.listFiles();
        if (files == null) {
            throw new IOException("Unable to list files in the specified directory.");
        }

        for (File file : files) {
            if (file.isFile()) {
                String fileName = file.getName();
                String newFileName = prefix + fileName;
                Path oldPath = file.toPath();
                Path newPath = Paths.get(directoryPath + File.separator + newFileName);

                Files.move(oldPath, newPath);
                // Log success or failure of file rename operation
            } else {
                // Log the file that was not renamed (e.g., directories or non-readable files)
            }
        }
    }

    // The Struts action method that handles the file renaming request
    public String execute() {
        try {
            String directoryPath = ServletActionContext.getRequest().getParameter("directoryPath");
            String prefix = ServletActionContext.getRequest().getParameter("prefix");
            renameFiles(directoryPath, prefix);
            return "success";
        } catch (IOException e) {
            // Log the error and return an error result code
            e.printStackTrace();
            return "error";
        }
    }
}
