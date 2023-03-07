package at.htlVillach.noahArsic.tetris;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
    private static final int WIDTH = 750, HEIGHT = 800;
    private static Handler handler;
    private static FieldHandler fh;
    private static PieceGenerator pg;
    private static HUD hud;
    private static KeyInput keyInput;
    private static Menu menu;
    private static GameOver gameOver;
    private static GAME_STATE gameState = GAME_STATE.Menu;
    private Thread thread;
    private boolean running = false;

    public enum GAME_STATE {
        Menu,
        Running,
        GameOver
    }

    public Game() {
        handler = new Handler();
        fh = new FieldHandler();
        pg = new PieceGenerator(this);
        hud = new HUD();
        menu = new Menu(this);
        keyInput = new KeyInput(handler);
        gameOver = new GameOver(this);
        this.addMouseListener(menu);
        new Window(HEIGHT, WIDTH, "Tetris", this);
    }

    public void initializeGame() {
        fh.initializeArray();
        handler.clearHandler();
        pg.initialize();
        hud = new HUD();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        double maxFPS = 60.0;
        long renderLastTime = System.nanoTime();
        double renderNs = 1000000000 / maxFPS;
        double renderDelta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            now = System.nanoTime();
            renderDelta += (now - renderLastTime) / renderNs;
            renderLastTime = now;
            while(running && renderDelta >= 1){
                render();
                frames++;
                renderDelta--;
            }
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                hud.setFrames(frames);
                frames = 0;
            }
        }
        stop();
    }

    public void tick() {
        handler.tick();
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        if (gameState == GAME_STATE.Menu) menu.render(g);
        else if (gameState == GAME_STATE.GameOver) {
            gameOver.render(g);
        }
        else {
            fh.render(g);
            handler.render(g);
            pg.render(g);
            hud.render(g);
        }
        g.dispose();
        bs.show();
    }

    public static KeyInput getKeyInput() {
        return keyInput;
    }

    public static GameOver getGameOver() {
        return gameOver;
    }

    public static void setGameState(GAME_STATE gameState) {
        Game.gameState = gameState;
    }

    public static int getGameWidth() {
        return WIDTH;
    }

    public static int getGameHeight() {
        return HEIGHT;
    }

    public static PieceGenerator getPg() {
        return pg;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static HUD getHud() {
        return hud;
    }

    public static void main(String[] args) {
	// write your code here
        new Game();
    }
}
