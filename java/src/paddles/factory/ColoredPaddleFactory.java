package paddles.factory;

import bridge.Blue;
import bridge.Red;
import bridge.White;
import paddles.abstractfactory.AbstractPaddleFactory;
import paddles.builder.AngledPaddleBuilder;
import paddles.builder.DashedPaddleBuilder;
import paddles.builder.SimplePaddleBuilder;
import paddles.Paddle;
import player.SelectedPlayer;

import bridge.ColorBridge;

import java.util.Random;

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
        ColorBridge color = getRandomColor();
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

    private ColorBridge getRandomColor() {
        ColorBridge[] colors = {
                new White(),
                new Blue(),
                new Red()
        };
        Random r = new Random();

        return colors[r.nextInt(colors.length)];
    }

    private Paddle createSimplePaddle(SelectedPlayer player, ColorBridge color) {
        return new SimplePaddleBuilder()
                .setColor(color)
                .setSelectedPlayer(player)
                .setSpeed(defaultSpeed)
                .build();
    }

    private Paddle createAngledPaddle(SelectedPlayer player, ColorBridge color) {
        return new AngledPaddleBuilder()
                .setAngle(defaultAngle)
                .setColor(color)
                .setSelectedPlayer(player)
                .setSpeed(defaultSpeed)
                .build();
    }

    private Paddle createDashedPaddle(SelectedPlayer player, ColorBridge color) {
        return new DashedPaddleBuilder()
                .setDashCount(defaultDashCount)
                .setColor(color)
                .setSelectedPlayer(player)
                .setSpeed(defaultSpeed)
                .build();
    }
}
