package CourseProject.Configuration;

import java.util.Properties;

public class DataSource {
    public String type;
    public Properties properties;

    public DataSource(String type, Properties properties) {
        this.type = type;
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "DataSource{" +
                "type='" + type + '\'' +
                ", properties=" + properties +
                '}';
    }
}
