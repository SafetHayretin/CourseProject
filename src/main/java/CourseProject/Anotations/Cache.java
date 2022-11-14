package CourseProject.Anotations;

public @interface Cache {
    String eviction();

    int flushInterval();

    int size();
}
