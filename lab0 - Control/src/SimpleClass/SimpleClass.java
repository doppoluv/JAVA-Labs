package SimpleClass;

import java.util.List;

public class SimpleClass {
    private String name;
    private int number;

    public void setName(String newName) {
        this.name = newName;
    }

    public String getName() {
        return name;
    }

    public void setNumber(int newNumber, boolean flag) {
        this.number = newNumber;
    }

    public int getNumber() {
        return number;
    }
}
