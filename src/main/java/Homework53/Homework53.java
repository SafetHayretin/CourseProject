package Homework53;


import CourseProject.Builders.*;
import java.io.IOException;
import java.util.List;

public class Homework53 {
    public static void main(String[] args) throws IOException {
        String resource = "src/main/resources/mybatis-config.xml";

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resource);
        SqlSession session = sqlSessionFactory.openSession();

        List<Employee> list = session.selectList("Homework53.Employee.getAll");
        System.out.println(list);
    }
}
