package CourseProject.Configuration;

public class Environment {
    public final String id;

    public  final String transactionManager;

    public final DataSource source;

    public Environment(String id, String manager, DataSource source) {
        this.id = id;
        this.transactionManager = manager;
        this.source = source;
    }

    @Override
    public String toString() {
        return "Environment{" +
                "id='" + id + '\'' +
                ", manager=" + transactionManager +
                ", source=" + source +
                '}';
    }
}
