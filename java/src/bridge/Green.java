package bridge;

import java.awt.*;

public class Green implements ColorBridge {
    @Override
    public Color color() {
        return Color.GREEN;
    }
}
