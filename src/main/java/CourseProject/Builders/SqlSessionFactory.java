package CourseProject.Builders;

import CourseProject.Configuration.Configuration;
import CourseProject.Configuration.DataSource;
import CourseProject.Configuration.Environment;
import CourseProject.DataBase.BasicDataBasePool;

import java.util.Properties;

public class SqlSessionFactory {
    private final Configuration configuration;

    public SqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSession()  {
        Environment env =  configuration.environments.environments.get(0);
        String transactionManager = env.transactionManager;
        if (!transactionManager.equals("JDBC")) {
            throw new RuntimeException("Transaction manager incorrect!");
        }

        DataSource source = env.source;
        String type =  source.type;
        if (type.equals("POOLED")) {
            Properties properties = source.properties;
            String url = (String) properties.get("url");
            String userName = (String) properties.get("username");
            String password = (String) properties.get("password");
            try {
                BasicDataBasePool.init(url, userName, password, 10);
            } catch (Exception e) {
                throw new RuntimeException("Can't create pool");
            }
        }

        return new SqlSession();
    }
}
