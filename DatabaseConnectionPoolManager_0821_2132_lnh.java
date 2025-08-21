// 代码生成时间: 2025-08-21 21:32:58
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DatabaseConnectionPoolManager.java
 * 
 * This class manages a database connection pool using Apache Commons DBCP.
 * It provides methods to get and release connections.
 * 
 * @author Your Name
 * @version 1.0
 */
public class DatabaseConnectionPoolManager extends ActionSupport {

    private DataSource dataSource;
    private BasicDataSource basicDataSource;

    // Constructor
    public DatabaseConnectionPoolManager() {
        // Initialize the basicDataSource with default properties
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Specify your JDBC driver here
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/your_database"); // Specify your database URL here
        basicDataSource.setUsername("root"); // Specify your database username here
        basicDataSource.setPassword("password"); // Specify your database password here

        // Set other properties like maxTotal, maxIdle, minIdle, etc. as needed
        basicDataSource.setMaxTotal(10);
        basicDataSource.setMaxIdle(5);
        basicDataSource.setMinIdle(2);
        basicDataSource.setInitialSize(2);

        dataSource = basicDataSource;
    }

    /**
     * Get a database connection from the pool.
     * 
     * @return Connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            // Handle the SQLException and re-throw it
            addActionError("Unable to get a connection from the pool: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Release a database connection back to the pool.
     * 
     * @param connection Connection
     */
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // Handle the SQLException
                addActionError("Unable to release the connection back to the pool: " + e.getMessage());
            }
        }
    }

    // Getter and Setter for dataSource
    public DataSource getDataSource() {
        return dataSource;
    }
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
