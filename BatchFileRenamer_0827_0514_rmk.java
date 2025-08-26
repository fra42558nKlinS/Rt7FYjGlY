// 代码生成时间: 2025-08-27 05:14:43
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * BatchFileRenamer is a utility class that allows renaming of files in a directory
 * based on a specified pattern.
 */
public class BatchFileRenamer {

    private String directoryPath; // Path to the directory containing files to rename
    private String extension;     // File extension to consider for renaming

    /**
     * Constructor for BatchFileRenamer.
     *
     * @param directoryPath The path to the directory where the files are located.
     * @param extension The file extension to target for renaming.
     */
    public BatchFileRenamer(String directoryPath, String extension) {
        this.directoryPath = directoryPath;
        this.extension = extension;
    }

    /**
     * Renames files in the directory with the given extension.
     *
     * @param renamePattern The pattern to use for renaming files.
     * @return A list of renamed files with their new names.
     */
    public List<String> renameFiles(String renamePattern) {
        File directory = new File(directoryPath);
        List<String> renamedFiles = new ArrayList<>();

        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("The specified directory does not exist or is not a directory.");
        }

        // List all files in the directory with the given extension
        File[] files = directory.listFiles((d, name) -> name.endsWith("." + extension));

        if (files == null) {
            return renamedFiles;
        }

        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            String newName = renamePattern.replace("{index}", String.valueOf(i));
            File newFile = new File(directory, newName);

            // Rename the file
            if (file.renameTo(newFile)) {
                renamedFiles.add(newName);
            } else {
                // Handle error in renaming
                System.err.println("Error renaming file: " + file.getName());
            }
        }

        return renamedFiles;
    }

    /**
     * Main method to test the BatchFileRenamer utility.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        try {
            // Example usage: rename all .txt files in the current directory to "file_{index}.txt"
            BatchFileRenamer renamer = new BatchFileRenamer(".", "txt");
            List<String> renamed = renamer.renameFiles("file_{index}.txt");
            System.out.println("Renamed files: " + renamed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
