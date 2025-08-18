// 代码生成时间: 2025-08-19 06:29:02
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * CSVBatchProcessor is a Struts2 action class that handles batch processing of CSV files.
 * It reads the uploaded CSV files, parses them, and executes the desired processing.
 */
@Namespace("/batch")
@Results({
    @Result(name = "success", type = "json"),
    @Result(name = "input", type = "json")
})
public class CSVBatchProcessor extends Struts2ActionSupport {

    // Action method to handle file upload and processing
    @Action(value = "processCSV", results = {@Result(name = "input", location = "input.jsp