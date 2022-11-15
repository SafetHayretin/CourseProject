package CourseProject.Anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Insert {
    String query();

    boolean useCache();
}
