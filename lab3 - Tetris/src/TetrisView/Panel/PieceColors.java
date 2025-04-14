package TetrisView.Panel;

import java.awt.*;

public class PieceColors {
    private static final Color[] colors = {
            null,
            new Color(71, 172, 179),   // I
            new Color(172, 179, 71),   // O
            new Color(179, 71, 130),   // T
            new Color(95, 179, 71),    // S
            new Color(179, 71, 71),    // Z
            new Color(71, 93, 179),    // J
            new Color(179, 111, 71)    // L
    };

    public static Color getColorWithId(int id) {
        return colors[id];
    }
}
