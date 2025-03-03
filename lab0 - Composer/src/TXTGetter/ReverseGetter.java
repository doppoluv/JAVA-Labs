package TXTGetter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReverseGetter implements Getter {
    private final List<String> lines;

    public ReverseGetter(List<String> lines) {
        this.lines = new ArrayList<>(lines);
        Collections.reverse(this.lines);
    }

    @Override
    public List<String> get() {
        return lines;
    }
}