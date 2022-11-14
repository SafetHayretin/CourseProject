package CourseProject.Configuration;

import CourseProject.Mappers.*;

import java.util.HashMap;

public class Mapper {
    public final String resource;

    public String nameSpace;

    public ResultMap resultMap;

    public Insert insert;

    public Delete delete;

    public HashMap<String, Select> selects = new HashMap<>();

    public Update update;

    public Mapper(String resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "Mapper{" +
                "resource='" + resource + '\'' +
                ", nameSpace='" + nameSpace + '\'' +
                ", resultMap=" + resultMap +
                ", insert=" + insert +
                ", delete=" + delete +
                ", update=" + update +
                '}';
    }
}
