package CourseProject.Anotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SelectAll {
    String query();

    boolean useCache();
}
