package CourseProject.Mappers;

public class Insert {
    public String id;

    public String parameterType;

    public
    String query;

    public Insert(String id, String parameterType, String query) {
        this.id = id;
        this.parameterType = parameterType;
        this.query = query;
    }

    @Override
    public String toString() {
        return "Insert{" +
                "id='" + id + '\'' +
                ", parameterType='" + parameterType + '\'' +
                ", query='" + query + '\'' +
                "} \n";
    }
}
