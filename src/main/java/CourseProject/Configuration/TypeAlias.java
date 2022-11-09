package CourseProject.Configuration;

public class TypeAlias {
    private String alias;
    private String type;

    public TypeAlias(String alias, String type) {
        this.alias = alias;
        this.type = type;
    }

    @Override
    public String toString() {
        return "TypeAlias{" +
                "alias='" + alias + '\'' +
                ", type='" + type + '\'' +
                "} \n";
    }
}
