package pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.net.URI;
import java.util.Random;
import java.awt.event.KeyEvent;

import api.PingPongSocketClient;
import com.google.gson.Gson;
import factory.PaddleFactory;
import factory.PaddleType;
import player.Player;
import player.SelectedPlayer;

/**
 * Implements the Runnable interface, so Game will be treated as a Thread to be executed
 * Included in java.lang This class contains all of the game logic and an inner class
 * for drawing the game.
 */
public class Game extends JFrame implements Runnable, KeyListener {

    //constants
    protected static final int WINDOW_HEIGHT = 500; // the height of the game window
    protected static final int WINDOW_WIDTH = 500; // the width of the game window
    // Scores
    protected int leftScore = 0;
    protected int rightScore = 0;
    //instance variables
    private Ball ball;
    private Random random = new Random(); //for generating random integers
    private boolean gameOver = true;
    //locks for concurrency
    private final Object ballLock = new Object();
    private final Object player1Lock = new Object();
    private final Object player2Lock = new Object();
    // Utils
    private Gson gson = new Gson(); // Could be in a singleton
    // Sockets
    private PingPongSocketClient client = null;
    // Players
    private Player player1; //player paddle
    private Player player2; //enemy paddle
    private SelectedPlayer selectedPlayer = SelectedPlayer.PLAYER2;
    private int currentPlayerYPosition = 0;
    // Paddles
    private PaddleFactory paddleFactory = new PaddleFactory();

    //where execution begins
    public static void main(String[] args) {
        new Game(); //create a new game object
    }

    //constructor for starting the game
    public Game() {
        addKeyListener(this);
        createConnection();
        initCanvas();
        createPlayers();
        //start the game
        startGameThread();
    }

    private void createPlayers() {
        player1 = new Player(
                true,
                paddleFactory.createPaddle(PaddleType.SIMPLE, SelectedPlayer.PLAYER1)
        );
        player2 = new Player(
                false,
                paddleFactory.createPaddle(PaddleType.SIMPLE, SelectedPlayer.PLAYER2)
        );
    }

    private Player getSelectedPlayer() {
        if(selectedPlayer == SelectedPlayer.PLAYER1) {
            return player1;
        }
        return player2;
    }

    private Player getEnemyPlayer() {
        if(selectedPlayer == SelectedPlayer.PLAYER1) {
            return player2;
        }
        return player1;
    }

    //Set up Canvas which is a child of Component and add it to (this) JFrame
    public void initCanvas() {
        Canvas myCanvas = new Canvas();
        myCanvas.setFocusable(true);

        //housekeeping for window stuff
        setLayout(new GridLayout());
        setTitle("Java Pong");
        setVisible(true);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setVisible(true);

        //if the window is not resize-able the window does not open on certain Linux machines
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //https://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        this.setLocationRelativeTo(null);

        add(myCanvas);

        //request focus so the JFrame is getting the input, for sure
        requestFocus();
    }

    //Starts the Thread which runs the game loop and various processing/updates
    public void startGameThread() {
        //set the game start running
        Thread gameThread = new Thread(this, "New Game Thread");
        try {
            //waits for this current thread to die before beginning execution
            gameThread.join();
            //most exceptions are contained in java.lang
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        //actually run the game
        gameThread.start();
    }

    private void createConnection() {
        String URL = "ws://localhost:8081";
        client = new PingPongSocketClient(URI.create(URL), this::onServerMessageReceived);
        client.connect();
    }

    private void onServerMessageReceived(String message) {
        try {
            BallPosition ballPosition = gson.fromJson(message, BallPosition.class);
            ball.setBallPosition(ballPosition);
            return;
        } catch (Exception e) {
            //
        }
        updateEnemyPlayerPosition(message);
    }

    private void updateSelectedPlayerPosition() {
        int selectedPlayerPosition = getSelectedPlayer().getPaddle().getYPosition();

        if (client != null && client.getConnection().isOpen()) {
            if (currentPlayerYPosition == selectedPlayerPosition) {
                return;
            }
            currentPlayerYPosition = selectedPlayerPosition;
            client.send(selectedPlayerPosition + "");
        }
    }

    private void updateEnemyPlayerPosition(String message) {
        getEnemyPlayer().getPaddle().setYPosition(Integer.parseInt(message));
    }

    public void run() {
		synchronized (ballLock) { // We will create the ball - make sure it doesn't get painted at the same time
			ball = new Ball(WINDOW_WIDTH / 2 - Ball.RADIUS, WINDOW_HEIGHT / 2 - Ball.RADIUS, 0);
		}
        /*Game loop should always be running*/
        boolean wallBounce = false;
        while (true) {
			try{
				int delayMs = 5;
				if (wallBounce)
				{
					delayMs = 200;//delay when a score happens so you can see where it's going to go
				}
		        Thread.sleep(delayMs);//tells the game how often to refresh
			}catch (Exception ex){
				System.out.println("Couldn't sleep for some reason.");
				ex.printStackTrace();
			}
            if (true) {
                updateSelectedPlayerPosition();
                if(getSelectedPlayer().isHost()) {
                    ball.updateBall();
                    destroyBall(); //point ball to null if it goes behind paddle (and creates a new one)
                    doCollision(); //checks for collisions between paddles and ball
                    wallBounce = checkWallBounce(); //for playing the wall sounds*/
                    // Send ball position
                    client.send(ball.getPosition().toJson());
                }
				/*
//				gameOver();*/

            } else { //Game Over, man!
//				ball.updateBall();
//				checkWallBounce();
            }
            repaint(); //repaint component (draw event in game maker), paintImmediately() is blocking (stops execution of other Threads)
        }
    }

    /*points the ball to null if it goes behind
    /either of the paddles*/
    public void destroyBall() {
        if (ball.isDestroyable()) {
            synchronized (ballLock) {
                ball = null;
            }//make sure does not get painted at same time
            /*creates the ball in the middle of the screen*/
            int ball_rand = random.nextInt(120);
            /*a ball_rand of 0 will create a ball that bounces vertically, forever */
            while (ball_rand == 0) {
                ball_rand = random.nextInt(120);
            }
            //System.out.println("ball seed " + ball_rand);
            //don't put the ball in the middle, it's impossible to react to in time.  Put it closer to the edge of the screen.
            synchronized (ballLock) {
                ball = new Ball((int) (WINDOW_WIDTH * 0.85), ball_rand + 120, (ball_rand + 120) * (Math.PI / 180));
            }
        }
    }

    //for playing the wall sounds, else-if because don't want any sounds to play or wall collision behavior to happen simultaneously
    public boolean checkWallBounce() {
        if ((ball.getYPos() >= (WINDOW_HEIGHT - (6 * Ball.RADIUS))) || (ball.getYPos() <= 0)) {
            //System.out.println("Top or bottom \'wall\' was hit");
        } else if (ball.getXPos() == (WINDOW_WIDTH - (4 * Ball.RADIUS))) {
            if (gameOver) {
            } else {
                leftScore++;
                return true;
            }
        } else if (ball.getXPos() == 0) {
            if (gameOver) {
            } else {
                rightScore++;
                return true;
            }
        }

        return false;
    }

    //Check for the moment where the paddles and the ball collide
	public void doCollision(){
		//left paddle collision
		for (int colY = player1.getPaddle().getYPosition(); colY <  player1.getPaddle().getYPosition() + SimplePaddle.HEIGHT; colY++){
			if (  ball.getXPos() ==  player1.getPaddle().getXPosition() &&   ball.getYPos() + Ball.RADIUS == colY){
				ball.reverseXVelocity();
				ball.setYVelocity(0);
				//System.out.println("COLLISION");
			}
		}

		//right paddle collision
		for (int colY = player2.getPaddle().getYPosition(); colY <  player2.getPaddle().getYPosition() + SimplePaddle.HEIGHT; colY++){
			if (ball.getXPos() ==  player2.getPaddle().getXPosition() - SimplePaddle.WIDTH &&  ball.getYPos() + Ball.RADIUS == colY){
				ball.reverseXVelocity();
				//System.out.println("COLLISION");
				ball.setYVelocity(0);
			}
		}
	}

    /*checks whether or not either of the paddles have scored 7 points -- if they have
    /then destroy the paddles and restart the game.*/
    public void gameOver() {
        if ((leftScore >= 7 || rightScore >= 7) && !gameOver) {
            gameOver = true;
            synchronized (player1Lock) {
                player1.setPaddle(null);
            }
            synchronized (player2Lock) {
                player2.setPaddle(null);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keycode = keyEvent.getKeyCode();
        if (keycode == KeyEvent.VK_UP) {
            getSelectedPlayer().getPaddle().moveUp();
            repaint();
        }
        if (keycode == KeyEvent.VK_DOWN) {
            getSelectedPlayer().getPaddle().moveDown();
            repaint();
        }
        if (gameOver && keycode == KeyEvent.VK_ENTER) {
            leftScore = 0;
            rightScore = 0;
            gameOver = false;
            synchronized (player1Lock) {
                player1.setPaddle(paddleFactory.createPaddle(PaddleType.SIMPLE, SelectedPlayer.PLAYER1));
            }
            synchronized (player2Lock) {
                player2.setPaddle(paddleFactory.createPaddle(PaddleType.SIMPLE, SelectedPlayer.PLAYER2));
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) { }

    //Nested class
    private class Canvas extends JPanel {

        //This method runs in a separate thread. Does not change state of Game data, only reads
        public void paint(Graphics g) {
            //weird swing graphics housekeeping
            Graphics2D g2 = (Graphics2D) g;

            //drawing the 'sprites' for the game
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, WINDOW_HEIGHT, WINDOW_WIDTH); // fill the whole screen black
            g2.setColor(Color.WHITE);

            //only draw the paddles when there is still a game in progress, and don't attempt to draw paddles when they are null
            if (!gameOver) {
                synchronized (player1Lock) { //wait until aquired lock from new game thread which has power to create and destroy the ball
                    if (player1 != null) {
                        g2.fillRect(player1.getPaddle().getXPosition(), player1.getPaddle().getYPosition(), SimplePaddle.WIDTH, SimplePaddle.HEIGHT); // draw player paddle
                    }
                }
                synchronized (player2Lock) {
                    if (player2 != null) {
                        g2.fillRect(player2.getPaddle().getXPosition(), player2.getPaddle().getYPosition(), SimplePaddle.WIDTH, SimplePaddle.HEIGHT); // draw computer paddle
                    }
                }
            } else {
                g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
                g2.drawString("Press the 'enter' key to start a new game.", 55, WINDOW_HEIGHT - 100);
            }

            synchronized (ballLock) { // Wait until nothing else is creating/deleting the ball
                if (ball != null) {
                    g2.fillOval(ball.getXPos(), ball.getYPos(), Ball.RADIUS * 2, Ball.RADIUS * 2);
                }
            }

            for (int i = 0; i < WINDOW_WIDTH; i += 10) { //dotted line
                g2.drawLine(WINDOW_WIDTH / 2, i, WINDOW_WIDTH / 2, i + 5);
            }

            g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 72));
            g2.drawString("" + leftScore, WINDOW_WIDTH / 2 - 150, 100);
            g2.drawString("" + rightScore, WINDOW_WIDTH / 2 + 100, 100);
        }
    }
}
