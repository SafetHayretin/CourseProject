package CourseProject.Mappers;

public class Select {
    private String id;

    private String resultMap;

    private String query;

    public Select(String id, String resultMap, String query) {
        this.id = id;
        this.resultMap = resultMap;
        this.query = query;
    }

    @Override
    public String toString() {
        return "Select{" +
                "id='" + id + '\'' +
                ", resultMap='" + resultMap + '\'' +
                ", query='" + query + '\'' +
                "} \n";
    }
}
