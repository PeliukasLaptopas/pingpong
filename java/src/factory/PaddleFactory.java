package factory;

import builder.AngledPaddleBuilder;
import builder.DashedPaddleBuilder;
import builder.SimplePaddleBuilder;
import player.SelectedPlayer;

public class PaddleFactory {

    private static PaddleFactory instance = null;
    private PaddleFactory(){}
    public static synchronized PaddleFactory getInstance() {
        if(instance == null) {
            instance = new PaddleFactory();
        }
        return instance;
    }

    private int defaultSpeed = 10;
    private int defaultAngle = 45;
    private int defaultDashCount = 2;

    public Paddle createPaddle(PaddleType paddleType, SelectedPlayer player) {
        switch (paddleType) {
            case SIMPLE:
                return createSimplePaddle(player);
            case DASHED:
                return createDashedPaddle(player);
            case ANGLED:
                return createAngledPaddle(player);
            default:
                return null;
        }
    }

    private Paddle createSimplePaddle(SelectedPlayer player) {
        return new SimplePaddleBuilder()
                .setSelectedPlayer(player)
                .setSpeed(defaultSpeed)
                .build();
    }

    private Paddle createAngledPaddle(SelectedPlayer player) {
        return new AngledPaddleBuilder()
                .setAngle(defaultAngle)
                .setSelectedPlayer(player)
                .setSpeed(defaultSpeed)
                .build();
    }

    private Paddle createDashedPaddle(SelectedPlayer player) {
        return new DashedPaddleBuilder()
                .setDashCount(defaultDashCount)
                .setSelectedPlayer(player)
                .setSpeed(defaultSpeed)
                .build();
    }
}
