package CourseProject.DAO;

import CourseProject.DataBase.BasicDataBasePool;
import Homework53.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    private final BasicDataBasePool pool;

    public EmployeeDao() throws Exception {
        this.pool = BasicDataBasePool.getConnectionPool();
        pool.manageConnections(5);
    }

    public int addEmployee(Employee employee) throws SQLException {
        PreparedStatement state;
        String sql = "insert into employee(first_name, last_name, email, phone_number,  salary) \n" +
                "VALUES(?, ?, ?, ?, ?)";

        Connection connection = pool.getConnection();
        state = connection.prepareStatement(sql);
        state.setString(1, employee.getFirst_name());
        state.setString(2, employee.getLast_name());
        state.setString(3, employee.getEmail());
        state.setString(4, employee.getPhoneNumber());
        state.setInt(5, employee.getSalary());
        state.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = state.getGeneratedKeys();
        int id = -1;
        if (rs.next()) {
            id = rs.getInt(1);
            employee.setId(id);
        }
        pool.releaseConnection(connection);

        return id;
    }

    public boolean deleteEmployee(int id) throws SQLException {
        PreparedStatement state;
        String sql = "delete from employee where employee_id = ?";

        Connection connection = pool.getConnection();
        state = connection.prepareStatement(sql);
        state.setInt(1, id);

        boolean result = state.execute();
        pool.releaseConnection(connection);
        return result;
    }

    public Employee getEmployeeById(int id) throws SQLException {
        PreparedStatement state;
        String sql = "SELECT employee.employee_id, employee.first_name, employee.last_name, employee.email, employee.phone_number,\n" +
                "employee.hire_date, employee.salary, jobs.title\n" +
                "FROM employee, jobs\n" +
                "WHERE jobs.job_id = employee.job_id AND employee.employee_id = ?";
        Connection connection = pool.getConnection();
        state = connection.prepareStatement(sql);
        state.setInt(1, id);
        ResultSet set = state.executeQuery();
        set.next();
        Employee result = new Employee(set.getInt(1), set.getString(2),
                set.getString(3), set.getString(4),
                set.getString(5), set.getDate(6),
                set.getInt(7), set.getString(8));
        pool.releaseConnection(connection);

        return result;
    }

    public List<Employee> getAllEmployees(String sql) throws SQLException {
        List<Employee> list = new ArrayList<>();
        PreparedStatement state;
        Connection connection = pool.getConnection();
        state = connection.prepareStatement(sql);
        ResultSet set = state.executeQuery(sql);
        while (set.next()) {
            list.add(new Employee(set.getInt(1), set.getString(2),
                    set.getString(3), set.getString(4),
                    set.getString(5), set.getDate(6),
                    set.getInt(7), set.getString(8)));
        }
        pool.releaseConnection(connection);

        return list;
    }

    public boolean updateEmployee(String sql, Employee employee) throws SQLException {
        PreparedStatement state;

        Connection connection = pool.getConnection();
        state = connection.prepareStatement(sql);
        state.setString(1, employee.getFirst_name());
        state.setString(2, employee.getLast_name());
        state.setString(3, employee.getEmail());
        state.setString(4, employee.getPhoneNumber());
        state.setString(5, employee.getHireDate().toString());
        state.setInt(6, employee.getSalary());
        state.setInt(7, employee.getId());
        int count = state.executeUpdate();
        pool.releaseConnection(connection);

        return count == 1;
    }

    public int updateEmployeeSalaries(float percent, float minSalary) throws SQLException {
        PreparedStatement state;
        String sql = "Update employee SET salary = salary * ? WHERE salary < ?";

        Connection connection = pool.getConnection();
        state = connection.prepareStatement(sql);
        state.setFloat(1, percent + 1);
        state.setFloat(2, minSalary);
        int affectedRows = state.executeUpdate();
        pool.releaseConnection(connection);

        return affectedRows;
    }

    public Employee getEmployeeByJobId(int job_id) throws SQLException {
        Employee employee = null;
        PreparedStatement state;

        String sql = "SELECT employee.employee_id, employee.first_name, employee.last_name, employee.email, employee.phone_number,\n" +
                "employee.hire_date, employee.salary, jobs.title\n" +
                "FROM employee, jobs\n" +
                "WHERE jobs.job_id = employee.job_id AND employee.job_id =" + job_id;
        Connection connection = pool.getConnection();
        state = connection.prepareStatement(sql);
        ResultSet set = state.executeQuery();
        if (set.next()) {
            employee = new Employee(set.getInt(1), set.getString(2),
                    set.getString(3), set.getString(4),
                    set.getString(5), set.getDate(6),
                    set.getInt(7), set.getString(8));
        }

        pool.releaseConnection(connection);

        return employee;
    }
}
