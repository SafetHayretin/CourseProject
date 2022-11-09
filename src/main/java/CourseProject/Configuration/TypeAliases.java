package CourseProject.Configuration;

import java.util.ArrayList;

public class TypeAliases {
    ArrayList<TypeAlias> typeAliases = new ArrayList<>();

    public void addTypeAlias(TypeAlias alias) {
        typeAliases.add(alias);
    }

    @Override
    public String toString() {
        return "TypeAliases{" +
                "typeAliases=" + typeAliases +
                "} \n";
    }
}
