package CourseProject.Configuration;


import java.util.ArrayList;

public class Environments {
    public ArrayList<Environment> environments = new ArrayList<>();

    public void addEnvironments(Environment env){
        environments.add(env);
    }

    @Override
    public String toString() {
        return "Environments{" +
                "environments=" + environments +
                "} \n";
    }
}
