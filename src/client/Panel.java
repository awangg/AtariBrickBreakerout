package client;

import objects.*;
import objects.balls.*;
import objects.tiles.*;
import objects.powerups.*;
import graphics.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Panel extends JPanel {

    // 1 - game     2 - dead     3 - win     5 - start     6 - instructions
    private int state = 5;
    private boolean begin = false, pause = false;
    private boolean keyboard = true, mouse = false;
    private boolean admin = false;

    private Timer mainTimer;
    private Timer fireSpread;

    private boolean[] keys = new boolean[512];

    public static Block[][] board;
    private int level = 1, tileWidth = 75, tileHeight = 40;

    public static Paddle player;
    private int playerWidth = 100, playerHeight = 15;
    public static int ballRadius = 20;

    public static ArrayList<Ball> balls;
    private int[] ballNums = new int[]{1, 1, 2, 2, 3};

    public static ArrayList<Effect> effects;
    public static long currentTime;
    private boolean spreadt;

    public static ArrayList<Powerup> powerups;
    public static int points;
    public static int lives;

    private JButton play, instructions, skip, back, chooseKeys, chooseMouse;
    private int buttonWidth = 100, buttonHeight = 50;

    private int[] gameOverOpacity = new int[]{0, 0, 0, 0, 0, 0, 0, 0}, victoryOpacity = new int[]{0, 0, 0, 0, 0, 0, 0};
    private String[] gameOver = new String[] {"G","A","M","E","O","V","E","R"}, victory = new String[]{"V","I","C","T","O","R","Y"};

    public Panel() {
        initButtons();

        mainTimer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(state == 1) {
                    currentTime = System.currentTimeMillis();

                    if(keyboard) playerControls();
                    if(!pause && begin) ballControls();
                    effectTimer();
                    powerupControls();

                    if(checkEmpty() && level < 5) {
                        pause = true;
                    }else if(checkEmpty() && level >= 5) {
                        state = 3;
                    }

                    if(lives <= 0) {
                        state = 2;
                    }
                }

                addButtons();
                repaint();
            }
        });
        mainTimer.start();

        fireSpread = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(state == 1) {
                    spreadt = false;
                    spreadFire();
                }
            }
        });
        fireSpread.start();

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                keys[e.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keys[e.getKeyCode()] = false;
            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(admin) {
                    int r = (e.getY() - (int) (tileHeight * 1.5)) / tileHeight;
                    int c = (e.getX()) / tileWidth;
                    if(r >= 0 && r < board.length && c >= 0 && c < board[r].length) {
                        Block bloc = board[r][c];
                        if (bloc.isExploding()) {
                            Exploding expl = (Exploding) bloc;
                            effects.add(new Explosion(bloc.getPosition().x + bloc.getWidth() / 2, bloc.getPosition().y + bloc.getHeight() / 2, tileWidth * 3, tileHeight * 3, currentTime));
                            board = expl.explode(board);
                        } else if (bloc.isMagic()) {
                            Magic mag = (Magic) bloc;
                            effects.add(new Sparkle(bloc.getPosition().x + bloc.getWidth() / 2, bloc.getPosition().y + bloc.getHeight() / 2, (int) (tileWidth * 1.15), tileHeight * 2, currentTime));
                            powerups.add(mag.spawnPowerup());
                        }
                        board[r][c] = null;
                    }
                }

                if(!begin) {
                    begin = true;
                }
                if(pause) {
                    begin = false;
                    level++;
                    loadLevel(level);
                    pause = false;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if(admin) {
                    Ball b = balls.get(0);
                    b.setPosition(new Point(e.getX(), e.getY()));
                }

                if(mouse && state == 1) player.setPosition(new Point(e.getX() - player.getWidth()/2, player.getPosition().y));
            }
        });
    }

    public void initButtons() {
        play = new JButton("Start");
        instructions = new JButton("How to Play");
        skip = new JButton("Skip Intro");
        back = new JButton("Home");
        chooseKeys = new JButton("Keyboard");
        chooseMouse = new JButton("Mouse");

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(play);
                remove(instructions);
                level = 1;
                loadLevel(level);
                state = 1;
            }
        });

        instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(play);
                remove(instructions);

                powerups = new ArrayList<>();
                powerups.add(new ExplodeTransform(20, 80, 30, 30));
                powerups.add(new MoreBalls(20, 130, 30, 30));
                powerups.add(new PointBooster(20, 180, 30, 30));
                powerups.add(new LifeUp(20, 230, 30, 30));

                state = 6;
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(back);
                remove(chooseKeys);
                remove(chooseMouse);
                gameOverOpacity = new int[]{0,0,0,0,0,0,0,0};
                victoryOpacity = new int[]{0,0,0,0,0,0,0,0};
                state = 5;
            }
        });

        chooseKeys.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(chooseKeys);
                add(chooseMouse);
                keyboard = false;
                mouse = true;
            }
        });

        chooseMouse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(chooseMouse);
                add(chooseKeys);
                mouse = false;
                keyboard = true;
            }
        });

        play.setBounds(Main.WIDTH/2 - buttonWidth/2, Main.HEIGHT/2 - 20, buttonWidth, buttonHeight);
        instructions.setBounds(Main.WIDTH/2 - buttonWidth/2, Main.HEIGHT/2 + 40, buttonWidth, buttonHeight);
        back.setBounds(Main.WIDTH - buttonWidth - 10, Main.HEIGHT - buttonHeight - 30, buttonWidth, buttonHeight);
        chooseMouse.setBounds(10, Main.HEIGHT - buttonHeight - 30, buttonWidth, buttonHeight);
        chooseKeys.setBounds(10, Main.HEIGHT - buttonHeight - 30, buttonWidth, buttonHeight);
    }

    public void addButtons() {
        if(state == 5) {
            add(play);
            add(instructions);
        }else if(state <= 3 || state == 6) {
            add(back);

            if(state == 6) {
                if(keyboard) {
                    add(chooseKeys);
                }else {
                    add(chooseMouse);
                }
            }
        }
    }

    public void loadLevel(int level) {
        board = new Block[5][8];
        int[][] setupLevel = new int[5][8];
        player = new Paddle(Main.WIDTH / 2 - playerWidth / 2, Main.HEIGHT - 100, playerWidth, playerHeight);
        balls = new ArrayList<>();
        effects = new ArrayList<>();
        powerups = new ArrayList<>();
        lives = 3;

        if(level == 1) {
            points = 0;
            setupLevel = new int[][] {
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 1, 1, 0, 0},
                    {0, 0, 1, 3, 3, 1, 0, 0},
                    {0, 0, 1, 1, 1, 1, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0}
            };
        }else if(level == 2) {
            setupLevel = new int[][] {
                    {1, 0, 1, 0, 1, 0, 1, 0},
                    {1, 0, 1, 0, 1, 0, 1, 0},
                    {1, 3, 1, 3, 1, 3, 1, 3},
                    {1, 0, 1, 0, 1, 0, 1, 0},
                    {1, 0, 2, 0, 2, 0, 1, 0}
            };
        }else if(level == 3) {
            setupLevel = new int[][] {
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {0, 0, 2, 0, 0, 2, 0, 0},
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {0, 0, 2, 0, 0, 2, 0, 0},
                    {1, 1, 1, 1, 1, 1, 1, 1}
            };
        }else if(level == 4) {
            setupLevel = new int[][] {
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 2, 1, 1, 1, 1, 2, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 3, 1, 1, 1, 1, 3, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1}
            };
        }else if(level == 5) {
            setupLevel = new int[][] {
                    {1, 1, 1, 1, 1, 1, 1, 1},
                    {2, 2, 2, 2, 2, 2, 2, 2},
                    {1, 3, 1, 3, 3, 1, 3, 1},
                    {2, 2, 2, 2, 2, 2, 2, 2},
                    {1, 1, 1, 1, 1, 1, 1, 1}
            };
        }
        resetBalls(ballNums[level - 1]);
        for(int r = 0; r < board.length; r++) {
            for(int c = 0; c < board[r].length; c++) {
                if(setupLevel[r][c] == 1) {
                    board[r][c] = new Block(c * tileWidth, r * tileHeight + (int)(tileHeight * 1.5), tileWidth, tileHeight);
                }else if(setupLevel[r][c] == 2) {
                    board[r][c] = new Magic(c * tileWidth, r * tileHeight + (int)(tileHeight * 1.5), tileWidth, tileHeight);
                }else if(setupLevel[r][c] == 3) {
                    board[r][c] = new Exploding(c * tileWidth, r * tileHeight + (int)(tileHeight * 1.5), tileWidth, tileHeight);
                }
            }
        }
    }

    public boolean checkEmpty() {
        for(int r = 0; r < board.length; r++) {
            for(int c = 0; c < board[r].length; c++) {
                if(board[r][c] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public void playerControls() {
        if(keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]) {
            player.move("left");
        }else if(keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]) {
            player.move("right");
        }else if(keys[KeyEvent.VK_0]) {
            if (level < 5) {
                begin = false;
                level++;
                loadLevel(level);
            } else {
                state = 3;
            }
            keys[KeyEvent.VK_0] = false;
        }
    }

    public void resetBalls(int num) {
        begin = false;
        for(int i = 0; i < num; i++) {
            double chance = Math.random();
            if(chance <= .75) {
                balls.add(new Ball(Main.WIDTH / 2, Main.HEIGHT - 150, ballRadius));
            }else if(chance <= .90) {
                balls.add(new Fireball(Main.WIDTH / 2, Main.HEIGHT - 150, ballRadius));
            }else {
                balls.add(new Thicc(Main.WIDTH / 2, Main.HEIGHT - 150, ballRadius * 5));
            }
        }
    }

    public void ballControls() {
        for(int i = 0; i < balls.size(); i++) {
            Ball b = balls.get(i);
            if(!admin) b.move();
            for(int r = 0; r < board.length; r++) {
                for(int c = 0; c < board[r].length; c++) {
                    Block bloc = board[r][c];
                    if(bloc != null && b.collide(bloc.getBounds())) {
                        if(bloc.isExploding()) {
                            Exploding expl = (Exploding)bloc;
                            effects.add(new Explosion(bloc.getPosition().x + bloc.getWidth()/2, bloc.getPosition().y + bloc.getHeight()/2, tileWidth * 3, tileHeight * 3, currentTime));
                            board = expl.explode(board);
                        }else if(bloc.isMagic()) {
                            Magic mag = (Magic)bloc;
                            effects.add(new Sparkle(bloc.getPosition().x + bloc.getWidth()/2, bloc.getPosition().y + bloc.getHeight()/2, (int)(tileWidth * 1.15), tileHeight * 2, currentTime));
                            powerups.add(mag.spawnPowerup());
                        }

                        if(b.isFireball()) {
                            bloc.setOnFire(true);
                            effects.add(new Fire(bloc.getPosition().x + bloc.getWidth()/2, bloc.getPosition().y + bloc.getHeight()/2, tileWidth, tileHeight, currentTime));
                        }else {
                            points += 20;
                            board[r][c] = null;
                        }
                    }
                }
            }
            b.collide(player.getBounds());

            if(b.hasHitBottom()) {
                balls.remove(b);
            }
            if(balls.isEmpty()) {
                resetBalls(ballNums[level - 1]);
                lives--;
                points -= 50;
                break;
            }
        }
    }

    public void effectTimer() {
        for(int i = 0; i < effects.size(); i++) {
            Effect e = effects.get(i);
            long deathtime = e.getBirth() + e.getLifetime();
            if(currentTime >= deathtime) {
                effects.remove(e);
            }
        }
    }

    public void powerupControls() {
        for(int i = 0; i < powerups.size(); i++) {
            Powerup p = powerups.get(i);
            p.move();
            if(p.isCollected(player)) {
                points += 10;
                p.action();
                powerups.remove(p);
            }

            if(p.getPosition().y > Main.HEIGHT) {
                powerups.remove(p);
            }
        }
    }

    public void spreadFire() {
        for(int r = 0; r < board.length; r++) {
            for(int c = 0; c < board[r].length; c++) {
                if(board[r][c] != null && board[r][c].isOnFire()) {
                    Block bloc = board[r][c];

                    int[] deltaX = new int[] {-1, 0, 1};
                    int[][] deltaY = new int[][] {
                            {-1, 0, 1},
                            {-1, 1},
                            {-1, 0, 1}
                    };

                    int index = (int)(Math.random() * deltaX.length);
                    int dx = deltaX[index];
                    int dy = deltaY[index][(int)(Math.random() * deltaY[index].length)];

                    if(r + dy >= 0 && r + dy < board.length && c + dx >= 0 && c + dx < board[r].length && board[r+dy][c+dx] != null && !spreadt) {
                        Block b = board[r+dy][c+dx];
                        board[r+dy][c+dx].setOnFire(true);
                        effects.add(new Fire(b.getPosition().x + b.getWidth()/2, b.getPosition().y + b.getHeight()/2, b.getWidth(), b.getHeight(), currentTime));
                        spreadt = true;
                    }

                    if(bloc.isExploding()) {
                        Exploding expl = (Exploding)bloc;
                        effects.add(new Explosion(bloc.getPosition().x + bloc.getWidth()/2, bloc.getPosition().y + bloc.getHeight()/2, tileWidth * 3, tileHeight * 3, currentTime));
                        board = expl.explode(board);
                    }else if(bloc.isMagic()) {
                        Magic mag = (Magic)bloc;
                        effects.add(new Sparkle(bloc.getPosition().x + bloc.getWidth()/2, bloc.getPosition().y + bloc.getHeight()/2, (int)(tileWidth * 1.15), tileHeight * 2, currentTime));
                        powerups.add(mag.spawnPowerup());
                    }

                    points += 20;
                    board[r][c] = null;
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Background
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

        if(state == 1) {

            // Draw Blocks
            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board[r].length; c++) {
                    if (board[r][c] != null) board[r][c].display(g2);
                }
            }

            // Draw Effects
            for (Effect e : effects) {
                e.display(g2);
            }

            // Draw Powerups
            for (Powerup p : powerups) {
                p.display(g2);
            }

            // Draw Balls
            for (Ball b : balls) {
                b.display(g2);
            }

            // Draw Player
            player.display(g2);

            // Start Prompt
            if(!begin) {
                String prompt = "Click to begin";
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
                g2.drawString(prompt, Main.WIDTH/2 - g2.getFontMetrics().stringWidth(prompt)/2, Main.HEIGHT/2 + 50);
            }

            if(pause) {
                String prompt = "Click to load next level";
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
                g2.drawString(prompt, Main.WIDTH/2 - g2.getFontMetrics().stringWidth(prompt)/2, Main.HEIGHT/2 + 50);
            }

            // Scoreboard
            String score = "Score: " + points;
            String livecount = "Lives: " + lives;
            String levelcount = "Level " + level;
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
            g2.drawString(score, Main.WIDTH - g2.getFontMetrics().stringWidth(score) - 10, 20);
            g2.drawString(livecount, Main.WIDTH - g2.getFontMetrics().stringWidth(livecount) - 10, 40);
            g2.drawString(levelcount, 10, 20);

        }else if(state == 2) {

            g2.setFont(new Font("Monospaced", Font.BOLD, 48));

            for(int i = 0; i < gameOver.length; i++) {
                if (gameOverOpacity[i] <= 255) {
                    g2.setColor(new Color(255, 0, 0, gameOverOpacity[i]));
                }else {
                    g2.setColor(Color.RED);
                }
                g2.drawString(gameOver[i], 70*i+30, Main.HEIGHT/2 - 24);
                gameOverOpacity[i] += (8-i);
            }

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 20));
            g2.drawString("Score: " + points, Main.WIDTH/2 - g2.getFontMetrics().stringWidth("Score: " + points)/2, Main.HEIGHT/2 + 20);

        }else if(state == 3) {

            g2.setFont(new Font("Monospaced", Font.BOLD, 48));
            for(int i = 0; i < victory.length; i++) {
                if (victoryOpacity[i] <= 255) {
                    g2.setColor(new Color(0, 255, 0, victoryOpacity[i]));
                }else {
                    g2.setColor(new Color(0, 255, 0));
                }
                g2.drawString(victory[i], 70*i+65, Main.HEIGHT/2 - 24);
                victoryOpacity[i] += (8-i);
            }

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 20));
            g2.drawString("Score: " + points, Main.WIDTH/2 - g2.getFontMetrics().stringWidth("Score: " + points)/2, Main.HEIGHT/2 + 20);

        }else if(state == 5) {

            // Text
            g2.setColor(new Color(66, 226, 244));
            g2.setFont(new Font("SansSerif", Font.BOLD, 36));
            String name = "Atari Brick Breaker-out";
            g2.drawString(name, Main.WIDTH/2 - g2.getFontMetrics().stringWidth(name)/2, Main.HEIGHT/2 - 60);

        }else if(state == 6) {

            // Controls
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 16));
            String cont;
            if(keyboard) {
                cont = "Controls:    A-Move Left    |    D-Move Right";
            }else {
                cont = "Controls:    Move Mouse";
            }
            g2.drawString(cont, Main.WIDTH/2 - g2.getFontMetrics().stringWidth(cont)/2, 30);

            // Powerups
            String power = "Powerups:";
            g2.drawString(power, 20, 65);
            for(Powerup p : powerups) {
                p.display(g2);
            }
            g2.drawString("- transforms some regular blocks to explosive blocks", 55, 99);
            g2.drawString("- adds up to four balls", 55, 149);
            g2.drawString("- gives 150 points", 55, 199);
            g2.drawString("- grants two more lives", 55, 249);

            String magicDesc = "|?| blocks drop powerups";
            String explDesc = "|!!!| blocks explode";
            String obj = "Make sure at least one ball stays above the ground";
            String goal = "Break all the blocks to advance";

            g2.setFont(new Font("Monospaced", Font.PLAIN, 24));
            g2.drawString(magicDesc, Main.WIDTH/2 - g2.getFontMetrics().stringWidth(magicDesc)/2, 300);
            g2.drawString(explDesc, Main.WIDTH/2 - g2.getFontMetrics().stringWidth(explDesc)/2, 340);

            g2.setFont(new Font("SansSerif", Font.PLAIN, 16));
            g2.setColor(Color.RED);
            g2.drawString(obj, Main.WIDTH/2 - g2.getFontMetrics().stringWidth(obj)/2, 380);
            g2.setColor(Color.GREEN);
            g2.drawString(goal, Main.WIDTH/2 - g2.getFontMetrics().stringWidth(goal)/2, 400);

        }

    }
}
