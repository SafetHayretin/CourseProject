package CourseProject.Configuration;

import Homework53.CustomEmpMapper;
import Homework53.EmployeeMapper;

import java.util.HashMap;

public class Configuration {
    public static Environments environments;
    public static TypeAliases aliases;

    public static Mappers mappers;

    private final HashMap<Class, EmployeeMapper> map = new HashMap<>();

    public <T> boolean addMapper(Class<T> clazz) {
        if (clazz.equals(EmployeeMapper.class)) {
            map.put(clazz, new CustomEmpMapper());
            return true;
        }
        return false;
    }

    public <T> EmployeeMapper getMapper(Class<T> clazz) {
        return map.get(clazz);
    }

    public static Mappers getMappers() {
        return mappers;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "environments=" + environments +
                ", aliases=" + aliases +
                ", mappers=" + mappers +
                '}';
    }
}
