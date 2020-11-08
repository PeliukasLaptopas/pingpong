package bridge;

import java.awt.*;

public class White implements ColorBridge {
    @Override
    public Color color() {
        return Color.WHITE;
    }
}