// 代码生成时间: 2025-09-01 00:16:42
import org.apache.struts2.StrutsStatics;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// 文件备份和同步工具类
public class FileBackupAndSyncTool {

    // 私有方法，用于复制文件
    private static void copyFile(File source, File dest) throws IOException {
        if (!dest.exists()) {
            dest.createNewFile();
        }
        try (FileInputStream inStream = new FileInputStream(source);
             FileOutputStream outStream = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }
        }
    }

    // 同步文件或文件夹的方法
    public static void syncFiles(File source, File dest) throws IOException {
        // 检查源文件是否存在
        if (!source.exists()) {
            throw new FileNotFoundException("Source file does not exist: " + source.getAbsolutePath());
        }

        // 检查目标文件夹是否存在，不存在则创建
        if (!dest.exists() && !dest.mkdirs()) {
            throw new IOException("Failed to create destination directory: " + dest.getAbsolutePath());
        }

        // 获取source和dest的文件属性
        try {
            BasicFileAttributes attrs = Files.readAttributes(source.toPath(), BasicFileAttributes.class);
            if (attrs.isDirectory()) {
                // 如果是目录，则递归同步子文件
                Files.walkFileTree(source.toPath(), new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        File targetDir = new File(dest, dir.toFile().getName());
                        if (!targetDir.exists()) {
                            targetDir.mkdir();
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        copyFile(file.toFile(), new File(dest, file.toFile().getName()));
                        return FileVisitResult.CONTINUE;
                    }
                });
            } else {
                // 如果是文件，则直接复制
                copyFile(source, dest);
            }
        } catch (IOException e) {
            throw new IOException("Error reading attributes of source file or directory: " + e.getMessage());
        }
    }

    // 异步执行文件同步
    public static void asyncSyncFiles(File source, File dest) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                syncFiles(source, dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        try {
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 主方法，用于测试文件同步功能
    public static void main(String[] args) {
        try {
            // 假设有一个源目录和一个目标目录
            File sourceDir = new File("C:/sourceDir");
            File destDir = new File("C:/destDir");

            // 异步同步文件
            asyncSyncFiles(sourceDir, destDir);

            System.out.println("File sync started...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
