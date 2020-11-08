package bridge;

import java.awt.*;

public class Red implements ColorBridge {
    @Override
    public Color color() {
        return Color.RED;
    }
}