package TetrisModel;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class HighScoresManager {
    private static final String HIGH_SCORES_FILE = "highscores.txt";

    public static void saveScore(int score) {
        if (score == 0) {
            return;
        }

        ArrayList<Integer> scores = new ArrayList<>();
        File file = new File(HIGH_SCORES_FILE);

        try {
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        scores.add(Integer.parseInt(line.trim()));
                    } catch (NumberFormatException e) {
                        // Игнорируем
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            e.fillInStackTrace();
        }

        scores.add(score);
        scores.sort(Collections.reverseOrder());
        while (scores.size() > 10) {
            scores.removeLast();
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Integer s : scores) {
                writer.write(s + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    public static ArrayList<Integer> getHighScores() {
        ArrayList<Integer> scores = new ArrayList<>();
        File file = new File(HIGH_SCORES_FILE);

        try {
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        scores.add(Integer.parseInt(line.trim()));
                    } catch (NumberFormatException e) {
                        // Игнорируем
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            e.fillInStackTrace();
        }

        scores.sort(Collections.reverseOrder());
        while (scores.size() > 10) {
            scores.removeLast();
        }

        return scores;
    }
}