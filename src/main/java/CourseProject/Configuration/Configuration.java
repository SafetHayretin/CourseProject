package CourseProject.Configuration;

import CourseProject.Mappers.CustomEmpMapper;
import CourseProject.Mappers.EmployeeMapper;
import CourseProject.Anotations.*;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;


import java.lang.reflect.Method;
import java.util.HashMap;

public class Configuration {
    public static Environments environments;
    public static TypeAliases aliases;

    public static Mappers mappers;

    private final HashMap<Class, EmployeeMapper> map = new HashMap<>();

    public <T> boolean addMapper(Class<T> clazz) {
        Mapper mapper = getAnnotations(clazz);

        if (clazz.equals(EmployeeMapper.class)) {
            map.put(clazz, new CustomEmpMapper(mapper));
            return true;
        }
        return false;
    }

    private <T> Mapper getAnnotations(Class<T> clazz) {
        Mapper mapper = new Mapper();

        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            if (m.isAnnotationPresent(Select.class)) {
                Select select = m.getAnnotation(Select.class);
                String sql = select.query();
                String id = m.getName();

                CourseProject.Mappers.Select sel = new CourseProject.Mappers.Select(id, null, sql);
                mapper.selects.put(id, sel);
            }
            if (m.isAnnotationPresent(Results.class)) {
                Results results = m.getAnnotation(Results.class);
                Result[] result = results.value();
                CourseProject.Mappers.ResultMap map = new CourseProject.Mappers.ResultMap();
                for (Result r : result) {
                    map.result.put(r.property(), r.column());
                }
            }
            if (m.isAnnotationPresent(Update.class)) {
                Update update = m.getAnnotation(Update.class);
                String sql = update.query();
                String id = m.getName();

                mapper.update = new CourseProject.Mappers.Update(id, null, sql);
            }
            if (m.isAnnotationPresent(Delete.class)) {
                Delete del = m.getAnnotation(Delete.class);
                String sql = del.query();
                String id = m.getName();

                mapper.delete = new CourseProject.Mappers.Delete(id, null, sql);
            }
            if (m.isAnnotationPresent(Insert.class)) {
                Insert insert = m.getAnnotation(Insert.class);
                String sql = insert.query();
                String id = m.getName();

                mapper.insert = new CourseProject.Mappers.Insert(id, null, sql);
            }
        }
        return mapper;
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
