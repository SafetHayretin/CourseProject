package CourseProject.DataBase;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

public class ConnectionHandler implements InvocationHandler {
    private final Connection connection;

    public ConnectionHandler(Connection con) {
        this.connection = con;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        if (name.contains("close")) {
            BasicDataBasePool pool = BasicDataBasePool.getConnectionPool();
            pool.releaseConnection(connection);
            System.out.println("close");
        }

        if (name.contains("prepareStatement")) {
            System.out.println(args[0]);
        }

        return method.invoke(connection, args);
    }
}
