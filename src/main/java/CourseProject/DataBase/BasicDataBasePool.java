package CourseProject.DataBase;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BasicDataBasePool implements DataBasePool {
    private static class ConNode {
        Connection connection;

        int minInactive = 0;

        ConNode(Connection connection) {
            this.connection = connection;
        }
    }

    private final String url;
    private final String user;
    private final String password;
    private final CircularLinkedList<ConNode> connectionList;
    private final List<ConNode> usedConnections = new ArrayList<>();

    private final int maxSize;

    private static BasicDataBasePool dataBasePool = null;

    public static void init(String url, String user, String password, int poolSize) throws Exception {
        if (dataBasePool != null)
            throw new Exception("Already created!");

        CircularLinkedList<ConNode> pool = new CircularLinkedList<>();
        for (int i = 0; i < poolSize; i++) {
            pool.addNode(new ConNode(createConnection(url, user, password)));
        }

        dataBasePool = new BasicDataBasePool(url, user, password, pool, poolSize);
    }

    public static BasicDataBasePool getConnectionPool() throws Exception {
        if (dataBasePool == null)
            throw new Exception("Not created!");

        return dataBasePool;
    }

    private BasicDataBasePool(String url, String user, String password, CircularLinkedList<ConNode> connectionList, int maxSize) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connectionList = connectionList;
        this.maxSize = maxSize;
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        Connection con = DriverManager.getConnection(url, user, password);
        Connection proxyCon = null;
        try {
             proxyCon = (Connection) withLogging(con);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return proxyCon;
    }

    public int getSize() {
        return connectionList.size() + usedConnections.size();
    }

    public void manageConnections(int minConnections) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                increaseTimeInactive();
                checkForInactiveConnections(minConnections);
            }
        };
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                try {
                    keepConnectionsActive();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        int min = 60_000;
        timer.schedule(task, 1000, min);
        timer.schedule(task1, 1000, min * 10);
    }

    private void keepConnectionsActive() throws SQLException {
        for (ConNode node : connectionList) {
            if (node.minInactive >= 5) {
                Connection connection = node.connection;
                Statement state = connection.prepareStatement("Select 1");
                state.execute("Select 1");
                node.minInactive = 0;
            }
        }
    }

    private void increaseTimeInactive() {
        for (ConNode node : connectionList) {
            node.minInactive++;
        }
    }

    private void checkForInactiveConnections(int minConnections) {
        for (ConNode node : connectionList) {
            if ((connectionList.size() + usedConnections.size()) == minConnections)
                break;
            if (node.minInactive > 10) {
                try {
                    node.connection.close();
                    connectionList.deleteNode(node);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static Object withLogging(Connection target) {
        return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class<?>[]{Connection.class}, new ConnectionHandler(target));
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connectionList.isEmpty()) {
            if (usedConnections.size() < maxSize) {
                connectionList.addNode(new ConNode(createConnection(url, user, password)));
            } else {
                throw new RuntimeException("Maximum pool size reached!");
            }
        }

        ConNode value = connectionList.head.getValue();
        connectionList.deleteNode(value);
        usedConnections.add(value);
        value.minInactive = 0;

        return value.connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        for (ConNode node : usedConnections) {
            if (node.connection == connection) {
                connectionList.addNode(node);
                return usedConnections.remove(connection);
            }
        }

        return false;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public String getUser() {
        return this.user;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
