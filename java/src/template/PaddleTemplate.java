package template;

import bridge.*;
import paddles.Paddle;
import paddles.builder.AngledPaddleBuilder;
import paddles.builder.DashedPaddleBuilder;
import paddles.builder.PaddleBuilder;
import paddles.builder.SimplePaddleBuilder;
import player.SelectedPlayer;

import java.util.Random;

public abstract class PaddleTemplate {

    private int defaultSpeed = 10;
    private int defaultAngle = 45;
    private int defaultDashCount = 2;

    public abstract boolean needsColor();

    public abstract boolean needsDashes();

    public abstract boolean needsAngle();

    public final Paddle createPaddleTemplate(SelectedPlayer player) {
        PaddleBuilder builder;
        if (needsAngle()) {
            builder = new AngledPaddleBuilder().setAngle(defaultAngle);
        } else if (needsDashes()) {
            builder = new DashedPaddleBuilder().setDashCount(defaultDashCount);
        } else {
            builder = new SimplePaddleBuilder();
        }
        if (needsColor()) {
            builder.setColor(getRandomColor());
        }
        return builder
                .setSpeed(defaultSpeed)
                .setSelectedPlayer(player)
                .build();
    }

    private ColorBridge getRandomColor() {
        ColorBridge[] colors = {
                new Green(),
                new Blue(),
                new Red()
        };
        Random r = new Random();

        return colors[r.nextInt(colors.length)];
    }
}
