package paddles.factory;

import paddles.abstractfactory.AbstractPaddleFactory;
import paddles.builder.AngledPaddleBuilder;
import paddles.builder.DashedPaddleBuilder;
import paddles.builder.SimplePaddleBuilder;
import paddles.Paddle;
import player.SelectedPlayer;

import java.awt.*;

public class ColoredPaddleFactory extends AbstractPaddleFactory {

    private static ColoredPaddleFactory instance = null;

    private ColoredPaddleFactory() {
    }

    public static synchronized ColoredPaddleFactory getInstance() {
        if (instance == null) {
            instance = new ColoredPaddleFactory();
        }
        return instance;
    }

    private int defaultSpeed = 10;
    private int defaultAngle = 45;
    private int defaultDashCount = 2;

    @Override
    public Paddle createPaddle(PaddleType paddleType, SelectedPlayer player) {
        Color color = getRandomColor();
        switch (paddleType) {
            case SIMPLE:
                return createSimplePaddle(player, color);
            case DASHED:
                return createDashedPaddle(player, color);
            case ANGLED:
                return createAngledPaddle(player, color);
            default:
                return null;
        }
    }

    private Color getRandomColor() {
        return new Color((int)(Math.random() * 0x1000000));
    }

    private Paddle createSimplePaddle(SelectedPlayer player, Color color) {
        return new SimplePaddleBuilder()
                .setColor(color)
                .setSelectedPlayer(player)
                .setSpeed(defaultSpeed)
                .build();
    }

    private Paddle createAngledPaddle(SelectedPlayer player, Color color) {
        return new AngledPaddleBuilder()
                .setAngle(defaultAngle)
                .setColor(color)
                .setSelectedPlayer(player)
                .setSpeed(defaultSpeed)
                .build();
    }

    private Paddle createDashedPaddle(SelectedPlayer player, Color color) {
        return new DashedPaddleBuilder()
                .setDashCount(defaultDashCount)
                .setColor(color)
                .setSelectedPlayer(player)
                .setSpeed(defaultSpeed)
                .build();
    }
}
