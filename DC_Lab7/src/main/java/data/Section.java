package data;

public class Section {
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
}
