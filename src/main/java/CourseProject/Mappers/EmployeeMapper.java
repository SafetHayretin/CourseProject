package CourseProject.Mappers;

import CourseProject.Anotations.*;
import CourseProject.Anotations.Delete;
import CourseProject.Anotations.Insert;
import CourseProject.Anotations.Select;
import CourseProject.Anotations.Update;
import CourseProject.Model.Employee;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;

import java.util.List;

@Cache(eviction = "FIFO", flushInterval = 60000, size = 512)
public interface EmployeeMapper {

    @SelectAll(query = "SELECT * FROM employee", useCache = true)
    @Results(value = {
            @Result(property = "id", column = "employee_id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "hireDate", column = "hire_date"),
            @Result(property = "salary", column = "salary"),
    })
    List<Employee> findAll();

    @Select(query = "SELECT FROM employee where id=#{id}", useCache = true)
    Employee selectEmployee(int id);

    @Delete(query = "SELECT FROM employee where id=#{id}", useCache = true)
    int deleteEmployee(Employee employee);

    String update = "UPDATE STUDENT SET EMAIL = #{email}, NAME = #{name}, BRANCH = #{branch}, PERCENTAGE = #{percentage}, PHONE =#{phone} WHERE ID = #{id};";

    @Update(query = update, useCache = true)
    int updateEmployee(Employee employee);

    String insert = "INSERT INTO EMPLOYEE (FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE, SALARY ) VALUES (#{firstName}, #{lastName}, #{email}, #{phoneNumber}, #{hireDate},#{salary})";

    @Insert(query = insert, useCache = true)
    int insertEmployee(Employee employee);
}
