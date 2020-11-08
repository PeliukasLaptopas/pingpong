package bridge;

import java.awt.*;

public class Blue implements ColorBridge {
    @Override
    public Color color() {
        return Color.BLUE;
    }
}