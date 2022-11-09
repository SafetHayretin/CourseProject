package CourseProject.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Mappers {
    private final List<Mapper> mappers = new ArrayList<>();

    public void addMapper(Mapper mapper) {
        mappers.add(mapper);
    }

    public Mapper getMapper(int index) {
        return mappers.get(index);
    }

    public List<Mapper> getAllMappers() {
        return mappers;
    }

    @Override
    public String toString() {
        return "Mappers{" +
                "mappers=" + mappers +
                "} \n";
    }
}
