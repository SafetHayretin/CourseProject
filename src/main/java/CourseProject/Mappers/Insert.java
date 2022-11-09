package CourseProject.Mappers;

public class Insert {
    private String id;

    private String parameterType;

    private String query;

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
