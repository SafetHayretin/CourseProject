package CourseProject.DataBase;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataBasePool {
    Connection getConnection() throws SQLException;
    boolean releaseConnection(Connection connection);
    String getUrl();
    String getUser();
    String getPassword();
}
