// 代码生成时间: 2025-09-16 13:16:26
import org.apache.struts2.ServletActionContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * FileBackupSyncTool.java - A file backup and synchronization tool using Java and Struts framework.
 *
 * @author Your Name
 * @version 1.0
 * @since 2023-04-01
 */
public class FileBackupSyncTool {

    /**
     * Backups a file to a specified directory.
     *
     * @param sourceFilePath The path of the file to backup.
     * @param backupDirectory The directory where the backup will be stored.
     * @return A message indicating the result of the backup operation.
     */
    public String backupFile(String sourceFilePath, String backupDirectory) {
        try {
            File sourceFile = new File(sourceFilePath);
            if (!sourceFile.exists()) {
                return "Error: Source file does not exist.";
            }

            File backupFile = new File(backupDirectory + File.separator + sourceFile.getName());
            Files.copy(Paths.get(sourceFilePath), Paths.get(backupFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
            return "Backup successful: " + backupFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error during backup: " + e.getMessage();
        }
    }

    /**
     * Synchronizes files between two directories.
     *
     * @param sourceDirectory The source directory.
     * @param targetDirectory The target directory.
     * @return A list of messages indicating the result of the synchronization operation.
     */
    public List<String> syncFiles(String sourceDirectory, String targetDirectory) {
        List<String> syncResults = new ArrayList<>();
        try {
            File sourceDir = new File(sourceDirectory);
            File targetDir = new File(targetDirectory);

            if (!sourceDir.exists() || !sourceDir.isDirectory()) {
                syncResults.add("Error: Source directory does not exist or is not a directory.");
                return syncResults;
            }

            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }

            File[] files = sourceDir.listFiles();
            if (files == null) {
                syncResults.add("Error: Unable to read source directory.");
                return syncResults;
            }

            for (File file : files) {
                if (file.isFile()) {
                    File targetFile = new File(targetDirectory + File.separator + file.getName());
                    if (!targetFile.exists() || targetFile.lastModified() < file.lastModified()) {
                        Files.copy(Paths.get(file.getAbsolutePath()), Paths.get(targetFile.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
                        syncResults.add("Sync successful: " + targetFile.getAbsolutePath());
                    }
                } else if (file.isDirectory()) {
                    // Recursively sync subdirectories
                    List<String> subSyncResults = syncFiles(file.getAbsolutePath(), targetDirectory + File.separator + file.getName());
                    syncResults.addAll(subSyncResults);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            syncResults.add("Error during synchronization: " + e.getMessage());
        }
        return syncResults;
    }
}
