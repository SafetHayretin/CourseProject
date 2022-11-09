package CourseProject.Mappers;

public class Update {
    private String id;

    private String parameterType;

    private String query;

    public Update(String id, String parameterType, String query) {
        this.id = id;
        this.parameterType = parameterType;
        this.query = query;
    }

    @Override
    public String toString() {
        return "Update{" +
                "id='" + id + '\'' +
                ", parameterType='" + parameterType + '\'' +
                ", query='" + query + '\'' +
                "} \n";
    }
}

