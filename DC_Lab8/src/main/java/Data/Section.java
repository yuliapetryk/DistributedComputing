package Data;

import java.io.Serializable;
import java.util.ArrayList;

public class Section implements Serializable {

    public int code;

    public String name;

    public Section(int code, String name){
        this.code = code;
        this.name = name;
    }

    public String getSectionName() {
            return this.name;
    }

    public Integer getSectionCode(){
        return this.code;
    }

    public void setSectionName(String name) {
        this.name = name;
    }

    public static String printSection(ArrayList<Section> sections){
        StringBuilder result = new StringBuilder();
        for (Section section :sections){
            result.append(">>").append(section.getSectionCode())
                    .append("-").append(section.getSectionName()).append("\n");
        }
        return result.toString();
    }

}
