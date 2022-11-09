package Homework53;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    @Select("select * from employee")
    List<Employee> findAll();

    @Select("SELECT * FROM employee WHERE id = #{id}")
    Employee selectEmployee(int id);

    @Delete("DELETE from Employee WHERE ID = #{id}")
    boolean deleteEmployee(int id);

    @Update("UPDATE STUDENT SET EMAIL = #{email}, NAME = #{name}, BRANCH = #{branch}, PERCENTAGE = #{percentage}, PHONE =#{phone} WHERE ID = #{id};")
    boolean updateEmployee(Employee employee);

    @Insert("INSERT INTO EMPLOYEE (FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE, SALARY ) VALUES (#{firstName}, #{lastName}, #{email}, #{phoneNumber}, #{hireDate},#{salary})")
    int insertEmployee(Employee employee);
}
