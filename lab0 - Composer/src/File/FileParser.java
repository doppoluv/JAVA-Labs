package File;

import Composer.ColumnMode;
import TXTGetter.DefaultGetter;
import TXTGetter.ReverseGetter;

import java.util.ArrayList;
import java.util.List;

public class FileParser {
    private List<String> leftColumn;
    private List<String> rightColumn;
    private int len;

    public void splitIntoColumns(List<String> lines, int columnLength) {
        this.len = columnLength;
        leftColumn = new ArrayList<>();
        rightColumn = new ArrayList<>();
        List<String> allWords = collectWords(lines);
        distributeWords(allWords);
    }

    private List<String> collectWords(List<String> lines) {
        List<String> allWords = new ArrayList<>();
        for (String line : lines) {
            String[] words = line.trim().split("\\s+");
            for (String word : words) {
                if (!word.isEmpty()) {
                    allWords.add(word);
                }
            }
        }
        return allWords;
    }

    private void distributeWords(List<String> allWords) {
        boolean addToLeft = true;
        List<String> currentLine = new ArrayList<>();
        int currentLength = 0;

        for (String word : allWords) {
            int wordLength = word.length();
            if (currentLength + wordLength + (currentLine.isEmpty() ? 0 : 1) <= len) {
                currentLine.add(word);
                currentLength += wordLength + (currentLine.size() == 1 ? 0 : 1);
            } else {
                if (!currentLine.isEmpty()) {
                    addLineToColumn(currentLine, addToLeft);
                    addToLeft = !addToLeft;
                }
                currentLine = new ArrayList<>();
                currentLine.add(word);
                currentLength = wordLength;
            }
        }
        if (!currentLine.isEmpty()) {
            addLineToColumn(currentLine, addToLeft);
        }
    }

    private void addLineToColumn(List<String> currentLine, boolean addToLeft) {
        String lineText = String.join(" ", currentLine);
        if (addToLeft) {
            leftColumn.add(lineText);
        } else {
            rightColumn.add(lineText);
        }
    }

    private List<String> getColumnLines(List<String> column, ColumnMode mode) {
        switch (mode) {
            case DEFAULT: return new DefaultGetter(column).get();
            case REVERSE: return new ReverseGetter(column).get();
            default: throw new IllegalArgumentException("Unsupported mode: " + mode);
        }
    }

    public List<String> getLeftColumn(ColumnMode leftMode) {
        return getColumnLines(leftColumn, leftMode);
    }

    public List<String> getRightColumn(ColumnMode rightMode) {
        return getColumnLines(rightColumn, rightMode);
    }

}
