package CourseProject.DAO;

import CourseProject.Configuration.Mapper;
import CourseProject.DataBase.BasicDataBasePool;
import CourseProject.Mappers.ResultMap;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomDAO {
    private final BasicDataBasePool pool;

    public Mapper mapper;
    public CustomDAO() throws Exception {
        this.pool = BasicDataBasePool.getConnectionPool();
    }

    public <T> T selectObject(int id, String sql, Class<T> c) throws SQLException, IllegalAccessException {
        Connection connection = pool.getConnection();

        String newSql = refactorSql(sql);
        PreparedStatement state = connection.prepareStatement(newSql);
        state.setInt(1, id);

        ResultSet set = state.executeQuery();
        set.next();
        ResultSetMetaData metaData = set.getMetaData();
        int count = metaData.getColumnCount();
        T obj = createObject(c, set, metaData, count);

        return obj;
    }

    public <T> List<T> selectList(String sql, Class<T> c, Object parameter) throws SQLException, IllegalAccessException {
        Connection connection = pool.getConnection();
        String newSql = refactorSql(sql);
        PreparedStatement state = connection.prepareStatement(newSql);

        prepareStatement(sql, parameter, state);

        ResultSet set = state.executeQuery();
        ResultSetMetaData metaData = set.getMetaData();
        int count = metaData.getColumnCount();
        List<T> list = new ArrayList<>();
        while (set.next()) {
            T obj = createObject(c, set, metaData, count);
            list.add(obj);
        }

        return list;
    }

    private <T> T createObject(Class<T> c, ResultSet set, ResultSetMetaData metaData, int count) throws IllegalAccessException, SQLException {
        T obj;
        try {
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

    public int insert(String sql, Object parameter) throws SQLException, IllegalAccessException {
        Connection connection = pool.getConnection();
        String newSql = refactorSql(sql);
        PreparedStatement state = connection.prepareStatement(newSql);

        prepareStatement(sql, parameter, state);

        int result = state.executeUpdate(newSql);
        pool.releaseConnection(connection);

        return result;
    }

    public int update(String sql, Object parameter) throws IllegalAccessException, SQLException {
        Connection connection = pool.getConnection();
        String newSql = refactorSql(sql);
        PreparedStatement state = connection.prepareStatement(newSql);

        prepareStatement(sql, parameter, state);

        int result = state.executeUpdate(newSql);
        pool.releaseConnection(connection);

        return result;
    }

    public int delete(String sql, Object parameter) throws SQLException, IllegalAccessException {
        Connection connection = pool.getConnection();
        String newSql = refactorSql(sql);
        PreparedStatement state = connection.prepareStatement(newSql);

        prepareStatement(sql, parameter, state);

        int result = state.executeUpdate(newSql);
        pool.releaseConnection(connection);

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
            return;
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

    private String refactorSql(String sql) {
        Pattern pattern = Pattern.compile("(#\\{)\\w+}");
        Matcher matcher = pattern.matcher(sql);

        return matcher.replaceAll("?");
    }

    private boolean isPrimitiveOrString(Object parameter) {
        Class<?> cl = parameter.getClass();
        if (cl.isPrimitive() || cl.equals(String.class))
            return true;
        return cl.equals(Integer.class) || cl.equals(Boolean.class) || cl.equals(Double.class) ||
                cl.equals(Short.class) || cl.equals(Float.class) || cl.equals(Long.class) || cl.equals(Character.class);
    }
}
