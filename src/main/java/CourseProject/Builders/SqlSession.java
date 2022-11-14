package CourseProject.Builders;

import CourseProject.Configuration.Configuration;
import CourseProject.Configuration.Mapper;
import CourseProject.Configuration.Mappers;
import CourseProject.DAO.CustomDAO;
import CourseProject.Mappers.Delete;
import CourseProject.Mappers.Insert;
import CourseProject.Mappers.Select;
import CourseProject.Mappers.Update;
import Homework53.Employee;
import Homework53.EmployeeMapper;

import java.util.List;

public class SqlSession {
    CustomDAO dao;

    private Configuration configuration;

    public SqlSession() {
        try {
            dao = new CustomDAO();
        } catch (Exception e) {
            throw new RuntimeException("Couldn't create Employee dao");
        }
    }

    public Configuration getConfiguration() {
        this.configuration = new Configuration();
        return configuration;
    }

    public <T> EmployeeMapper getMapper(Class<T> clazz) {
        return configuration.getMapper(clazz);
    }

    public int update(String command, Employee emp) {
        Mappers mappers = Configuration.getMappers();
        Mapper mapper = mappers.getMapper(0);
        dao.mapper = mapper;
        Update update = mapper.update;
        if (!update.id.equals(command)) {
            throw new RuntimeException("No such update id!");
        }
        String query = update.query;
        int result = 0;
        try {
           result =  dao.update(query, emp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public int insert(String command, Employee emp) {
        Mappers mappers = Configuration.getMappers();
        Mapper mapper = mappers.getMapper(0);
        Insert insert = mapper.insert;
        if (!insert.id.equals(command)) {
            throw new RuntimeException("No such insert id!");
        }
        String query = insert.query;
        int result = 0;
        try {
            result =  dao.insert(query, emp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public Employee select(String command, int id) {
        Mappers mappers = Configuration.getMappers();
        Mapper mapper = mappers.getMapper(0);
        dao.mapper = mapper;
        Select select = mapper.selects.get(command);
        String query = select.query;
        Employee emp = null;
        try {
            emp = dao.selectObject(id, query, Employee.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emp;
    }

    public List<Employee> selectList(String command) {
        Mappers mappers = Configuration.getMappers();
        Mapper mapper = mappers.getMapper(0);
        dao.mapper = mapper;
        Select select = mapper.selects.get(command);
        String query = select.query;
        List<Employee> employees = null;
        try {
            employees = dao.selectList(query, Employee.class, new Employee());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employees;
    }

    public int delete(String command, Employee emp) {
        Mappers mappers = Configuration.getMappers();
        Mapper mapper = mappers.getMapper(0);
        Delete delete = mapper.delete;
        if (!delete.id.equals(command)) {
            throw new RuntimeException("No such delete id!");
        }
        String query = delete.query;
        int result = 0;
        try {
            result =  dao.delete(query, emp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
