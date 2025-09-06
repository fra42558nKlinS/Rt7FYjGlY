// 代码生成时间: 2025-09-07 07:28:49
import org.apache.struts2.ServletActionContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileBackupAndSyncTool {

    private static final String SOURCE_DIR = "/path/to/source"; // 源目录路径
    private static final String DEST_DIR = "/path/to/destination"; // 目标目录路径
    private static final String BACKUP_DIR = "/path/to/backup"; // 备份目录路径

    public static void main(String[] args) {
        try {
            // 同步文件
            syncFiles(SOURCE_DIR, DEST_DIR);
            // 备份文件
            backupFiles(DEST_DIR, BACKUP_DIR);
            System.out.println("文件备份和同步完成。");
        } catch (Exception e) {
            e.printStackTrace();
            ServletActionContext.getRequest().setAttribute("error", e.getMessage());
        }
    }

    private static void syncFiles(String sourceDir, String destDir) throws IOException {
        // 确保源目录和目标目录存在
        File source = new File(sourceDir);
        File dest = new File(destDir);
        if (!source.exists() || !source.isDirectory()) {
            throw new IllegalArgumentException("源目录不存在或不是目录。");
        }
        if (!dest.exists() || !dest.isDirectory()) {
            throw new IllegalArgumentException("目标目录不存在或不是目录。");
        }

        // 遍历源目录中的文件
        for (File file : source.listFiles()) {
            File destFile = new File(dest, file.getName());
            if (file.isFile()) {
                // 如果目标目录中的文件不存在，则复制文件
                if (!destFile.exists()) {
                    copyFile(file, destFile);
                } else {
                    // 如果文件存在，比较文件大小和修改时间，决定是否覆盖
                    if (file.length() != destFile.length() || file.lastModified() > destFile.lastModified()) {
                        copyFile(file, destFile);
                    }
                }
            } else {
                // 如果是目录，则递归同步文件
                syncFiles(file.getAbsolutePath(), destFile.getAbsolutePath());
            }
        }
    }

    private static void backupFiles(String sourceDir, String backupDir) throws IOException {
        // 确保备份目录存在
        File backup = new File(backupDir);
        if (!backup.exists() || !backup.isDirectory()) {
            throw new IllegalArgumentException("备份目录不存在或不是目录。");
        }

        // 遍历目标目录中的文件
        for (File file : new File(sourceDir).listFiles()) {
            File backupFile = new File(backupDir, file.getName());
            if (file.isFile()) {
                // 复制文件到备份目录
                copyFile(file, backupFile);
            } else {
                // 如果是目录，则递归备份文件
                backupFiles(file.getAbsolutePath(), backupFile.getAbsolutePath());
            }
        }
    }

    private static void copyFile(File sourceFile, File destFile) throws IOException {
        // 使用NIO的Files.copy方法进行文件复制
        Files.copy(Paths.get(sourceFile.getAbsolutePath()), Paths.get(destFile.getAbsolutePath()),
                StandardCopyOption.REPLACE_EXISTING);
    }
}
