package ball;

import sound.GameSound;
import utils.CanvasConstants;
import visitor.BallAngleVisitor;
import visitor.SmoothBallAngleVisitor;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Ball implements GameSound {

    private double xVelocity, yVelocity;
    public double angle;
    private BallPosition position;
    //Collision handling
    private Timer lastCollisionTimer = new Timer();
    private boolean isCollisionEnabled = true;
    private boolean destroyable = false; //is this field necessary?
    public Shape ballShape;
    private BallAngleVisitor angleVisitor;

    public abstract int getSize();

    public abstract int getSpeed();

    public Ball() {
        angle = 1;
        addAngleVisitor(new SmoothBallAngleVisitor());
        position = new BallPosition(CanvasConstants.WINDOW_WIDTH / 2 - getSize(), CanvasConstants.WINDOW_HEIGHT / 2 - getSize());

        //init the velocity in both directions
        xVelocity = (Math.cos(angle) * (double) getSpeed());
        if (xVelocity == 0) {
            xVelocity = getSpeed();
        }
        yVelocity = (Math.sin(angle) * (double) getSpeed());
        destroyable = false;
        System.out.println(angle);
    }

    public void addAngleVisitor(BallAngleVisitor visitor) {
        this.angleVisitor = visitor;
    }

    @Override
    public void makeSound() {
        System.out.println("Created a ball");
    }

    private TimerTask createCollisionTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                isCollisionEnabled = true;
            }
        };
    }

    public void onCollision() {
        if (isCollisionEnabled) {
            isCollisionEnabled = false;
            lastCollisionTimer.cancel();
            lastCollisionTimer = new Timer();
            lastCollisionTimer.schedule(createCollisionTimerTask(), 1000);
            updateAngle();
            reverseXVelocity();
        }
    }

    public void updateAngle() {
        angle = angleVisitor.calculateAngle(this);
        yVelocity = (Math.sin(angle) * (double) getSpeed());
        System.out.println("Current angle: " + angle);
    }

    public void draw(Graphics2D g2) {
        ballShape = new Ellipse2D.Double(getXPos(), getYPos(), getSize() * 2, getSize() * 2);
        g2.fill(ballShape);
    }

    //update position
    public void updateBall() {
        position.setX(position.getX() + xVelocity);
        position.setY(position.getY() + yVelocity);
        //System.out.println(angle);
        //right bound checking
        if (position.getX() > CanvasConstants.WINDOW_WIDTH_ACTUAL - getSize()) {
            reverseXVelocity();
            destroyable = true;
        }
        //left bound checking
        if (position.getX() < 0) {
            reverseXVelocity();
            destroyable = true;
        }
        //down bound checking
        if (position.getY() > CanvasConstants.WINDOW_HEIGHT_ACTUAL - getSize()) {
            reverseYVelocity();

        }
        //upper bound checking
        if (position.getY() < 0) {
            reverseYVelocity();
        }
    }

    public void setBallPosition(BallPosition position) {
        this.position = position;
    }

    //getters
    public double getXPos() {
        return position.getX();
    }

    public double getYPos() {
        return position.getY();
    }

    public BallPosition getPosition() {
        return position;
    }

    //for swapping directions
    public void reverseXVelocity() {
        xVelocity = -xVelocity;
    }

    public void reverseYVelocity() {
        yVelocity = -yVelocity;
    }

    public boolean isDestroyable() {
        return destroyable;
    }

    //setter
    public void setYVelocity(int y) {
        yVelocity = y;
    }

}
