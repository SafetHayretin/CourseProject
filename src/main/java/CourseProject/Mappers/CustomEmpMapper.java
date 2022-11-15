package CourseProject.Mappers;

import CourseProject.Configuration.Mapper;
import CourseProject.DAO.GenerationalCachePQ;
import CourseProject.DataBase.BasicDataBasePool;
import CourseProject.Model.Employee;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomEmpMapper implements EmployeeMapper {
    private BasicDataBasePool pool;

    private GenerationalCachePQ cachePQ;

    public Mapper mapper;

    public CustomEmpMapper(Mapper mapper) {
        try {
            this.pool = BasicDataBasePool.getConnectionPool();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mapper = mapper;

    }

    @Override
    public List<Employee> findAll() {
        Select select = mapper.selects.get("findAll");
        List<Employee> list = new ArrayList<>();

        try {
            Connection connection = pool.getConnection();
            String sql = select.query;
            String newSql = refactorSql(sql);
            PreparedStatement state = connection.prepareStatement(newSql);

            prepareStatement(sql, new Employee(), state);

            ResultSet set = state.executeQuery();
            ResultSetMetaData metaData = set.getMetaData();
            int count = metaData.getColumnCount();
            while (set.next()) {
                Employee obj = createObject(set, metaData, count);
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;
    }

    @Override
    public Employee selectEmployee(int id) {
        Select select = mapper.selects.get("selectEmployee");
        Employee obj = null;

        try {
            Connection connection = pool.getConnection();

            String newSql = refactorSql(select.query);
            PreparedStatement state = connection.prepareStatement(newSql);
            state.setInt(1, id);

            ResultSet set = state.executeQuery();
            set.next();
            ResultSetMetaData metaData = set.getMetaData();
            int count = metaData.getColumnCount();
            obj = createObject(set, metaData, count);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return obj;
    }

    @Override
    public int deleteEmployee(Employee employee) {
        Delete delete = mapper.delete;
        int result = 0;
        try {
            Connection connection = pool.getConnection();
            String sql = delete.query;
            String newSql = refactorSql(sql);
            PreparedStatement state = connection.prepareStatement(newSql);

            prepareStatement(sql, employee, state);

            result = state.executeUpdate(newSql);
            pool.releaseConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int insertEmployee(Employee employee) {
        Insert insert = mapper.insert;
        int result = 0;
        try {
            Connection connection = pool.getConnection();
            String sql = insert.query;
            String newSql = refactorSql(sql);
            PreparedStatement state = connection.prepareStatement(newSql);

            prepareStatement(sql, employee, state);

            result = state.executeUpdate(newSql);
            pool.releaseConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int updateEmployee(Employee employee) {
        Update update = mapper.update;
        int result = 0;
        try {
            Connection connection = pool.getConnection();
            String sql = update.query;
            String newSql = refactorSql(sql);
            PreparedStatement state = connection.prepareStatement(newSql);

            prepareStatement(sql, employee, state);

            result = state.executeUpdate(newSql);
            pool.releaseConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private void prepareStatement(String sql, Object parameter, PreparedStatement state) throws SQLException, IllegalAccessException {
        Class<?> cl = parameter.getClass();

        if (isPrimitiveOrString(parameter)) {
            setState(state, parameter, 1);
        } else {
            Field[] fields = cl.getDeclaredFields();
            List<String> parameters = getParameterNames(sql);

            for (int i = 0; i < parameters.size(); i++) {
                String paramName = parameters.get(i);
                for (Field f : fields) {
                    if (f.getName().equals(paramName)) {
                        setState(state, f.get(parameter), i + 1);
                        break;
                    }
                }
            }
        }
    }

    private List<String> getParameterNames(String sql) {
        List<String> params = new ArrayList<>();
        Pattern pattern = Pattern.compile("(?<=#\\{).+?(?=})");
        Matcher matcher = pattern.matcher(sql);

        while (matcher.find()) {
            params.add(matcher.group());
        }

        return params;
    }

    private void setState(PreparedStatement state, Object f, int i) throws SQLException {
        if (f instanceof String valueOf) {
            state.setString(i, valueOf);
            return;
        }
        if (f instanceof Float valueOf) {
            state.setFloat(i, valueOf);
            return;
        }
        if (f instanceof Integer valueOf) {
            state.setInt(i, valueOf);
            return;
        }
        if (f instanceof Boolean valueOf) {
            state.setBoolean(i, valueOf);
        }
    }

    private boolean isPrimitiveOrString(Object parameter) {
        Class<?> cl = parameter.getClass();
        if (cl.isPrimitive() || cl.equals(String.class))
            return true;
        return cl.equals(Integer.class) || cl.equals(Boolean.class) || cl.equals(Double.class) ||
                cl.equals(Short.class) || cl.equals(Float.class) || cl.equals(Long.class) || cl.equals(Character.class);
    }

    private Employee createObject(ResultSet set, ResultSetMetaData metaData, int count) throws IllegalAccessException, SQLException {
        Employee obj;
        try {
            Class<Employee> c = Employee.class;
            obj = c.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }

        Field[] fields = obj.getClass().getDeclaredFields();
        ResultMap map = mapper.resultMap;
        HashMap<String, String> names = map.result;
        System.out.println(names);
        for (int i = 1; i <= count; i++) {
            String name = metaData.getColumnName(i);
            for (Field f : fields) {
                f.setAccessible(true);
                String sqlName = names.get(f.getName());

                if (sqlName != null && sqlName.equals(name)) {
                    Class<?> type = f.getType();
                    if (type.equals(String.class)) {
                        f.set(obj, set.getString(i));
                        break;
                    }
                    if (type.equals(int.class)) {
                        f.set(obj, set.getInt(i));
                        break;
                    }
                    if (type.equals(Date.class)) {
                        f.set(obj, set.getDate(i));
                        break;
                    }
                    if (type.equals(double.class)) {
                        f.set(obj, set.getDouble(i));
                        break;
                    }
                    if (type.equals(Boolean.class)) {
                        f.set(obj, set.getBoolean(i));
                        break;
                    }
                    break;
                }
            }
        }

        return obj;
    }

    private String refactorSql(String sql) {
        Pattern pattern = Pattern.compile("(#\\{)\\w+}");
        Matcher matcher = pattern.matcher(sql);

        return matcher.replaceAll("?");
    }
}
