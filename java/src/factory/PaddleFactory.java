package factory;

import paddles.AngledPaddle;
import paddles.DashedPaddle;
import player.SelectedPlayer;
import paddles.SimplePaddle;

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
        PaddleBuilder builder = new PaddleBuilder();
        builder.setSelectedPlayer(player);
        builder.setSpeed(defaultSpeed);

        switch (paddleType) {
            case SIMPLE:
                return builder.createPaddle(PaddleType.SIMPLE, defaultDashCount, defaultAngle);
            case DASHED:
                return builder.createPaddle(PaddleType.DASHED, defaultDashCount, defaultAngle);
            case ANGLED:
                return builder.createPaddle(PaddleType.ANGLED, defaultDashCount, defaultAngle);
            default:
                return null;
        }
    }
}
