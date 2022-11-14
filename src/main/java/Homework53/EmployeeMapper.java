package Homework53;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    @Select("select * from employee")
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

    @Select("SELECT * FROM employee WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "employee_id"),
            @Result(property = "firstName", column = "first_name"),
            @Result(property = "lastName", column = "last_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "hireDate", column = "hire_date"),
            @Result(property = "salary", column = "salary"),
    })
    Employee selectEmployee(int id);

    @Delete("DELETE from Employee WHERE ID = #{id}")
    boolean deleteEmployee(int id);

    @Update("UPDATE STUDENT SET EMAIL = #{email}, NAME = #{name}, BRANCH = #{branch}, PERCENTAGE = #{percentage}, PHONE =#{phone} WHERE ID = #{id};")
    boolean updateEmployee(Employee employee);

    @Insert("INSERT INTO EMPLOYEE (FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE, SALARY ) VALUES (#{firstName}, #{lastName}, #{email}, #{phoneNumber}, #{hireDate},#{salary})")
    int insertEmployee(Employee employee);
}
