package CourseProject.Mappers;

public class Delete {
    public String id;

    public String parameterType;

    public String query;

    public Delete(String id, String parameterType, String query) {
        this.id = id;
        this.parameterType = parameterType;
        this.query = query;
    }

    @Override
    public String toString() {
        return "Delete{" +
                "id='" + id + '\'' +
                ", parameterType='" + parameterType + '\'' +
                ", query='" + query + '\'' +
                "} \n";
    }
}