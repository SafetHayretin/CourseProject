package Homework53;

import java.util.List;

public class CustomEmpMapper implements EmployeeMapper {
    @Override
    public List<Employee> findAll() {
        return null;
    }

    @Override
    public Employee selectEmployee(int id) {
        return null;
    }

    @Override
    public boolean deleteEmployee(int id) {
        return false;
    }

    @Override
    public int insertEmployee(Employee employee) {
        return 0;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        return false;
    }
}
