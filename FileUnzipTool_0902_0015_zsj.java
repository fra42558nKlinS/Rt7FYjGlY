// 代码生成时间: 2025-09-02 00:15:44
package com.example.unzip;

import org.apache.struts2.ServletActionContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUnzipTool {

    /**
     * Unzips a compressed file to a specified directory.
     * 
     * @param zipFilePath The path to the zip file.
     * @param destDirectory The destination directory where files will be unzipped.
     * @throws IOException If an I/O error occurs.
     */
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // If the entry is a file, extract it
                extractFile(zipIn, filePath);
            } else {
                // If the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    /**
     * Extracts a file from a zip input stream and writes it to the file system.
     * 
     * @param zipIn The zip input stream.
     * @param filePath The path to the file to be extracted.
     * @throws IOException If an I/O error occurs.
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            int len;
            while ((len = zipIn.read(buffer.array())) > 0) {
                fos.write(buffer.array(), 0, len);
            }
        }
    }
}
