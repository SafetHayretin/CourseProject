package CourseProject.Mappers;

import java.util.HashMap;

public class ResultMap {
    private String id;
    private String type;

    public HashMap<String, String> result = new HashMap<>();

    public ResultMap() {

    }

    public ResultMap(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public void add(String property, String column) {
        if (property == null || column == null)
            throw new IllegalArgumentException("Wrong arguments");
        result.put(property, column);
    }

    @Override
    public String toString() {
        return "ResultMap{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", result=" + result +
                "} \n";
    }
}
