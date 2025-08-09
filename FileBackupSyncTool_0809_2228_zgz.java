// 代码生成时间: 2025-08-09 22:28:38
import org.apache.struts2.StrutsStatics;
import com.opensymphony.xwork2.ActionSupport;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.apache.commons.io.FileUtils;

/**
 * 文件备份和同步工具
 *
 * 功能：
 * 1. 同步源目录到目标目录，确保目标目录与源目录一致。
 * 2. 备份文件到指定目录。
 */
public class FileBackupSyncTool extends ActionSupport {

    /**
     * 同步源目录到目标目录
     *
     * @param sourcePath 源目录路径
     * @param targetPath 目标目录路径
     * @return 同步结果消息
     */
    public String syncDirectories(String sourcePath, String targetPath) {
        try {
            // 确保源目录和目标目录存在
            File sourceDir = new File(sourcePath);
            File targetDir = new File(targetPath);
            if (!sourceDir.exists() || !sourceDir.isDirectory()) {
                addActionError("Source directory does not exist or is not a directory.");
                return StrutsStatics.ERROR;
            }
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }

            // 同步文件
            syncFiles(sourceDir, targetDir);
            return StrutsStatics.SUCCESS;
        } catch (Exception e) {
            this.addActionError("Error syncing directories: " + e.getMessage());
            return StrutsStatics.ERROR;
        }
    }

    /**
     * 备份文件到指定目录
     *
     * @param sourcePath 源文件路径
     * @param backupPath 备份目录路径
     * @return 备份结果消息
     */
    public String backupFile(String sourcePath, String backupPath) {
        try {
            // 确保源文件存在且备份目录存在
            File sourceFile = new File(sourcePath);
            File backupDir = new File(backupPath);
            if (!sourceFile.exists() || !sourceFile.isFile()) {
                addActionError("Source file does not exist or is not a file.");
                return StrutsStatics.ERROR;
            }
            if (!backupDir.exists()) {
                backupDir.mkdirs();
            }

            // 备份文件
            File backupFile = new File(backupDir, sourceFile.getName());
            FileUtils.copyFile(sourceFile, backupFile);
            return StrutsStatics.SUCCESS;
        } catch (Exception e) {
            this.addActionError("Error backing up file: " + e.getMessage());
            return StrutsStatics.ERROR;
        }
    }

    /**
     * 同步文件
     * 递归同步源目录和目标目录中的文件和子目录
     *
     * @param sourceDir 源目录
     * @param targetDir 目标目录
     */
    private void syncFiles(File sourceDir, File targetDir) throws IOException {
        File[] sourceFiles = sourceDir.listFiles();
        if (sourceFiles != null) {
            for (File sourceFile : sourceFiles) {
                File targetFile = new File(targetDir, sourceFile.getName());

                if (sourceFile.isDirectory()) {
                    if (!targetFile.exists()) {
                        targetFile.mkdirs();
                    }
                    syncFiles(sourceFile, targetFile);
                } else {
                    if (!targetFile.exists() || sourceFile.lastModified() > targetFile.lastModified()) {
                        FileUtils.copyFile(sourceFile, targetFile);
                    }
                }
            }
        }
    }
}
