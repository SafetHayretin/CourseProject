package CourseProject.Configuration;

public class Configuration {
    public static Environments environments;
    public static TypeAliases aliases;

    public static Mappers mappers;

    public static Mappers getMappers() {
        return mappers;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "environments=" + environments +
                ", aliases=" + aliases +
                ", mappers=" + mappers +
                '}';
    }
}
