package Composer;

import java.util.List;

public class Composer {
    private final List<String> leftColumn;
    private final List<String> rightColumn;

    public Composer(List<String> left, List<String> right) {
        this.leftColumn = left;
        this.rightColumn = right;
    }

    public void printColumns() {
        int maxLines = Math.max(leftColumn.size(), rightColumn.size());
        for (int i = 0; i < maxLines; i++) {
            String leftText = (i < leftColumn.size()) ? leftColumn.get(i) : "";
            String rightText = (i < rightColumn.size()) ? rightColumn.get(i) : "";
            System.out.printf("%s | %s%n", leftText, rightText);
        }
    }
}