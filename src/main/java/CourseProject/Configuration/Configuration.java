package CourseProject.Configuration;

public class Configuration {
    public Environments environments;
    public TypeAliases aliases;

    public Mappers mappers;

    @Override
    public String toString() {
        return "Configuration{" +
                "environments=" + environments +
                ", aliases=" + aliases +
                ", mappers=" + mappers +
                '}';
    }
}
