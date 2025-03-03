import Composer.*;
import File.*;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        FileReader fr = new FileReader();
        fr.read("text.txt");
        List<String> content = fr.getList();

        FileParser fp = new FileParser();
        fp.splitIntoColumns(content,30);
        List<String> left = fp.getLeftColumn(ColumnMode.DEFAULT);
        List<String> right = fp.getRightColumn(ColumnMode.REVERSE);

        Composer composer = new Composer(left, right);
        composer.printColumns();
    }
}