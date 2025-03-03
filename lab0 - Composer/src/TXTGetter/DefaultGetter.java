package TXTGetter;

import java.util.List;

public class DefaultGetter implements Getter {
    private final List<String> lines;

    public DefaultGetter(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public List<String> get() {
        return lines;
    }
}