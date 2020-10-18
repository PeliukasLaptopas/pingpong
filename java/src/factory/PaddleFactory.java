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
        switch (paddleType) {
            case SIMPLE:
                return new SimplePaddle(player, defaultSpeed);
            case DASHED:
                return new DashedPaddle(player, defaultSpeed, defaultDashCount);
            case ANGLED:
                return new AngledPaddle(player, defaultSpeed, defaultAngle);
            default:
                return null;
        }
    }
}
