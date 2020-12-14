import api.PingPongSocketClient;
import ball.Ball;
import ball.BallPosition;
import ball.factory.BallFactory;
import ball.factory.BallType;
import bridge.Red;
import com.google.gson.Gson;
import command.Action;
import command.CommandManager;
import command.PaddleCollisionActionLeft;
import command.PaddleCollisionActionRight;
import input.InputHandler;
import input.InputKey;
import observer.InputKeyObserver;
import observer.StringObserver;
import paddles.Paddle;
import paddles.SimplePaddle;
import paddles.abstractfactory.AbstractPaddleFactory;
import paddles.abstractfactory.PaddleFactoryProducer;
import paddles.abstractfactory.PaddleFactoryType;
import paddles.factory.PaddleType;
import patterns.chain_of_responsibility.Logger;
import patterns.interpreter.InterpretedAction;
import patterns.interpreter.Interpreter;
import patterns.iterator.Iterator;
import patterns.iterator.PlayerList;
import patterns.proxy.CreatePlayer1Proxy;
import patterns.state.*;
import patterns.state.Menu;
import patterns.template.AngledPaddleTemplate;
import patterns.template.ColoredPaddleTemplate;
import player.Player;
import player.SelectedPlayer;
import strategy.Context;
import strategy.OperationAdd;
import utils.CanvasConstants;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Implements the Runnable interface, so Game will be treated as a Thread to be executed
 * Included in java.lang This class contains all of the game logic and an inner class
 * for drawing the game.
 */
public class Game extends JFrame implements Runnable {

    private final String URL = "ws://localhost:8081";
    // Scores
    protected int leftScore = 0;
    protected int rightScore = 0;

    GameStatusState statusState = new Menu();

    CommandManager manager = CommandManager.getInstance();
    List<Action> actionList = new ArrayList<>();
    Context context = new Context(new OperationAdd());

    //instance variables
    private Ball ball;
    private BallType ballType = BallType.LARGE;
    private PaddleType paddleType = PaddleType.SIMPLE;
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

    private List<Ball.BounceData> ballBounceCount = new ArrayList<Ball.BounceData>();

    private Player player1; //player paddle
    private Player player2; //enemy paddle
    private PlayerList players;
    private SelectedPlayer selectedPlayer = SelectedPlayer.PLAYER1;
    private int currentPlayerYPosition = 0;
    // Paddles
    private AbstractPaddleFactory paddleFactory = PaddleFactoryProducer.getFactory(PaddleFactoryType.COLORED);
    private BallFactory ballFactory = new BallFactory();
    // Input
    private InputHandler inputHandler = new InputHandler();

    //where execution begins
    public static void main(String[] args) throws InterruptedException {
        new Game(); //create a new game object
    }

    //constructor for starting the game
    public Game() throws InterruptedException {
        Ball.BounceData newBounce = new Ball.BounceData(0);
        ballBounceCount.add(newBounce);

        statusState = new Loading();
        createConnection();
        initCanvas();
        addKeyListener(inputHandler);
        createPlayers();
        //start the game
        startGameThread();
        readInput();

        statusState = new Menu();
    }

    private void readInput() {
        Scanner scanner = new Scanner(System.in);
        String input;
        Interpreter interpreter = new Interpreter();
        while (!(input = scanner.nextLine()).equals("exit")) {
            InterpretedAction action = interpreter.interpret(input);
            handleInterpretedAction(action, true);
        }
    }

    private void handleInterpretedAction(InterpretedAction action, boolean isLocal) {
        switch (action) {
            case BALL_SMALL: {
                ballType = BallType.SMALL;
                createBall();
                break;
            }
            case BALL_MEDIUM: {
                ballType = BallType.MEDIUM;
                createBall();
                break;
            }
            case BALL_LARGE: {
                ballType = BallType.LARGE;
                createBall();
                break;
            }
            case PADDLE_ANGLED: {
                paddleType = PaddleType.ANGLED;
                if(isLocal) {
                    createSelectedPlayer();
                } else {
                    createEnemyPlayer();
                }
                createBall();
                break;
            }
            case PADDLE_SIMPLE: {
                paddleType = PaddleType.SIMPLE;
                if(isLocal) {
                    createSelectedPlayer();
                } else {
                    createEnemyPlayer();
                }
                createBall();
                break;
            }
        }
        if (isLocal && client != null && client.getConnection().isOpen()) {
            client.send(action.toJson());
        }
    }

    private void createConnection() {
        client = new PingPongSocketClient(URI.create(URL));
        client.connect();
        setUpObservers();
    }

    private void setUpObservers() {
        new StringObserver(client.getSubject(), this::onClientResponse);
        new InputKeyObserver(inputHandler.getSubject(), this::onKeyPressed);
    }

    private void createSelectedPlayer() {
        if (selectedPlayer == SelectedPlayer.PLAYER1) {
            SimplePaddle p = (SimplePaddle)paddleFactory.createPaddle(paddleType, SelectedPlayer.PLAYER1);
            p.setColor(new Red());

            player1 = new Player(
                    true,
                    p,
                    SelectedPlayer.PLAYER1
            );
        } else {
            player2 = new Player(
                    false,
                    paddleFactory.createPaddle(paddleType, SelectedPlayer.PLAYER2),
                    SelectedPlayer.PLAYER2
            );
        }
    }

    private void createEnemyPlayer() {
        if (selectedPlayer == SelectedPlayer.PLAYER1) {
            player2 = new Player(
                    true,
                    paddleFactory.createPaddle(paddleType, SelectedPlayer.PLAYER2),
                    SelectedPlayer.PLAYER1
            );
        } else {
            player1 = new Player(
                    false,
                    paddleFactory.createPaddle(paddleType, SelectedPlayer.PLAYER1),
                    SelectedPlayer.PLAYER2
            );
        }
    }

    private void createBall() {
        ball = ballFactory.createBall(ballType);

        //ewww this shouldnt be like this but whatever
        actionList.clear();
        actionList.add(new PaddleCollisionActionLeft(player1.getPaddle(), ball, "Left"));
        actionList.add(new PaddleCollisionActionRight(player2.getPaddle(), ball, "Right"));

        Logger.log(Logger.VERBOSE, "" + actionList.size());
    }

    private void createPlayers() throws InterruptedException {
        CreatePlayer1Proxy cp1p = new CreatePlayer1Proxy(true );

        player1 = cp1p.newPlayer();

        player2 = player1.makeCopy();
        player2.setHost(false);
        Paddle angledPaddle = new AngledPaddleTemplate().createPaddleTemplate(SelectedPlayer.PLAYER2);
        player2.setPaddle(angledPaddle);
        List<Player> playerList = new ArrayList<Player>() {{
            add(player1);
            add(player2);
        }};
        players = new PlayerList(playerList);
        for (Iterator<Player> iterator = players.getIterator(); iterator.hasNext(); ) {
            Logger.log(Logger.INFO, "Created player -> " + iterator.next().getSelectedPlayer());
        }
    }

    private Player getSelectedPlayer() {
        if (selectedPlayer == SelectedPlayer.PLAYER1) {
            return player1;
        }
        return player2;
    }

    private Player getEnemyPlayer() {
        if (selectedPlayer == SelectedPlayer.PLAYER1) {
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
        setSize(CanvasConstants.WINDOW_WIDTH, CanvasConstants.WINDOW_HEIGHT);
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

    private void onClientResponse(String message) {
        try {
            BallPosition ballPosition = gson.fromJson(message, BallPosition.class);
            ball.setBallPosition(ballPosition);
            return;
        } catch (Exception e) {
            Logger.log(Logger.WARN, "Could not parse Ball position");
        }
        try {
            InterpretedAction action = gson.fromJson(message, InterpretedAction.class);
            handleInterpretedAction(action, false);
            return;
        } catch (Exception e) {
            Logger.log(Logger.WARN, "Could not parse Interpreted action");
        }
        getEnemyPlayer().getPaddle().setYPosition(Integer.parseInt(message));
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

    public void run() {
        synchronized (ballLock) { // We will create the ball - make sure it doesn't get painted at the same time
            createBall();
        }
        /*Game loop should always be running*/
        boolean wallBounce = false;
        while (true) {
            try {
                int delayMs = 5;
                if (wallBounce) {
                    delayMs = 200;//delay when a score happens so you can see where it's going to go
                }
                Thread.sleep(delayMs);//tells the game how often to refresh
            } catch (Exception ex) {
                Logger.log(Logger.ERROR, "Couldn't sleep for some reason.");
                ex.printStackTrace();
            }
            if (true) {
                updateSelectedPlayerPosition();
                if (getSelectedPlayer().isHost()) {
                    ball.updateBall();
                    wallBounce = checkWallBounce(); //for playing the wall sounds*/
                    destroyBall(); //point ball to null if it goes behind paddle (and creates a new one)
                    doCollision(); //checks for collisions between paddles and ball
                    // Send ball position
                    if (client != null && client.getConnection().isOpen()) {
                        client.send(ball.getPosition().toJson());
                    }
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
            //don't put the ball in the middle, it's impossible to react to in time.  Put it closer to the edge of the screen.
            synchronized (ballLock) {
                /*ball = new Ball((int) (CanvasConstants.WINDOW_WIDTH * 0.85), ball_rand + 120, (ball_rand + 120) * (Math.PI / 180));*/
                createBall();
            }
        }
    }

    //for playing the wall sounds, else-if because don't want any sounds to play or wall collision behavior to happen simultaneously
    public boolean checkWallBounce() {
        if ((ball.getYPos() >= (CanvasConstants.WINDOW_HEIGHT_ACTUAL)) || (ball.getYPos() <= 0)) {
            ballBounceCount.get(ballBounceCount.size() - 1).count++;

            Logger.log(Logger.INFO, "Top ot bottom wall was hit.");
        } else if (ball.getXPos() >= (CanvasConstants.WINDOW_WIDTH_ACTUAL - ball.getSize())) {
            if (gameOver) {
            } else {
                leftScore = context.executeStrategy(leftScore, 1);
                return true;
            }
        } else if (ball.getXPos() <= 0) {
            if (gameOver) {
            } else {
                rightScore = context.executeStrategy(rightScore, 1);
                return true;
            }
        }

        return false;
    }

    //Check for the moment where the paddles and the ball collide
    public void doCollision() {
        statusState = new CollisionState();
        manager.execute(actionList);

//        player1.getPaddle().doCollision(ball);
//        player2.getPaddle().doCollision(ball);
    }

    /*checks whether or not either of the paddles have scored 7 points -- if they have
    /then destroy the paddles and restart the game.*/
    public void gameOver() {
        if ((leftScore >= 7 || rightScore >= 7) && !gameOver) {
            Ball.BounceData newBounce = new Ball.BounceData(0);
            ballBounceCount.add(newBounce);

            gameOver = true;
            statusState = new Exit();
            synchronized (player1Lock) {
                player1.setPaddle(null);
            }
            synchronized (player2Lock) {
                player2.setPaddle(null);
            }
        }
    }

    private void onKeyPressed(InputKey inputKey) {
        if (inputKey == InputKey.UP) {
            getSelectedPlayer().getPaddle().moveUp();
            repaint();
        }
        if (inputKey == InputKey.DOWN) {
            getSelectedPlayer().getPaddle().moveDown();
            repaint();
        }
        if (inputKey == InputKey.ENTER) {
            if (gameOver) {
                leftScore = 0;
                rightScore = 0;
                gameOver = false;
            }
        }
    }

    //Nested class
    private class Canvas extends JPanel {

        private class CanvasFacade {
            public void setupCanvas(Graphics g) {
                //weird swing graphics housekeeping
                Graphics2D g2 = (Graphics2D) g;

                //drawing the 'sprites' for the game
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, CanvasConstants.WINDOW_HEIGHT, CanvasConstants.WINDOW_WIDTH); // fill the whole screen black
                g2.setColor(Color.WHITE);

                //only draw the paddles when there is still a game in progress, and don't attempt to draw paddles when they are null
                if (!gameOver) {
                    synchronized (player1Lock) { //wait until aquired lock from new game thread which has power to create and destroy the ball
                        if (player1 != null) {
                            player1.getPaddle().draw(g2);
                        }
                    }
                    synchronized (player2Lock) {
                        if (player2 != null) {
                            player2.getPaddle().draw(g2);
                        }
                    }
                } else {
                    g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
                    g2.drawString("Press the 'enter' key to start a new game.", 55, CanvasConstants.WINDOW_HEIGHT - 100);
                }

                synchronized (ballLock) { // Wait until nothing else is creating/deleting the ball
                    if (ball != null) {
                        ball.draw(g2);
                    }
                }

                for (int i = 0; i < CanvasConstants.WINDOW_WIDTH; i += 10) { //dotted line
                    g2.drawLine(CanvasConstants.WINDOW_WIDTH / 2, i, CanvasConstants.WINDOW_WIDTH / 2, i + 5);
                }

                g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 72));
                g2.drawString("" + leftScore, CanvasConstants.WINDOW_WIDTH / 2 - 150, 100);
                g2.drawString("" + rightScore, CanvasConstants.WINDOW_WIDTH / 2 + 100, 100);
                g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
                g2.drawString("game bounces: " + ballBounceCount.get(ballBounceCount.size()-1).count, CanvasConstants.WINDOW_WIDTH / 2 - 50, 100);
            }
        }

        CanvasFacade c = new CanvasFacade();

        //This method runs in a separate thread. Does not change state of Game data, only reads
        public void paint(Graphics g) {
            c.setupCanvas(g);
        }
    }
}