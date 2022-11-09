package Homework53;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;

public class Homework53 {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        Employee employee = new Employee("Safet", "Kyuchyukhalil", "sft@gmail.com", "0896185599", new Date(2020 - 10 - 14), 1500, "5");

        List<Employee> list = session.selectList("Homework53.Employee.getAll");
        System.out.println(list);
        session.commit();
        session.close();
    }
}
