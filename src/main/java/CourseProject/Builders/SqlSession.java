package CourseProject.Builders;

import CourseProject.Configuration.Configuration;
import CourseProject.Configuration.Mapper;
import CourseProject.Configuration.Mappers;
import CourseProject.DAO.EmployeeDao;
import CourseProject.Mappers.Select;
import CourseProject.Mappers.Update;
import Homework53.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlSession {
    EmployeeDao dao;

    public SqlSession() {
        try {
            dao = new EmployeeDao();
        } catch (Exception e) {
            throw new RuntimeException("Couldn't create Employee dao");
        }
    }

    public int update(String command, Employee emp) {
        Mappers mappers = Configuration.getMappers();
        Mapper mapper = mappers.getMapper(0);
        Update update = mapper.update;
        String query = update.query;
        query = refactorSql(query);
        try {
            dao.updateEmployee(query, emp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public int insert(String command) {
        return -1;
    }

    public List<Employee> selectList(String command) {
        Mappers mappers = Configuration.getMappers();
        Mapper mapper = mappers.getMapper(0);
        Select select = mapper.select;
        String query = select.query;
        List<Employee> employees = null;
        try {
            employees = dao.getAllEmployees(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public int delete(String command) {
        return -1;
    }

    private String refactorSql(String sql) {
        Pattern pattern = Pattern.compile("(#\\{)\\w+}");
        Matcher matcher = pattern.matcher(sql);

        return matcher.replaceAll("?");
    }
}
