package at.htlVillach.noahArsic.tetris;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameOver extends MouseAdapter {
    private final Game game;

    public GameOver(Game game) {
        this.game = game;
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.drawString("Game Over", 200, 250);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Score: " +Game.getHud().getScore(), 305, 300);
        g.drawString("Play", 330, 450);
        g.drawRect(295, 417, 125, 50);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (mx >= 295 && mx <= 420 && my >= 417 && my <= 467) {
            Game.setGameState(Game.GAME_STATE.Running);
            game.initializeGame();
            game.addKeyListener(Game.getKeyInput());
            game.removeMouseListener(this);
        }
    }
}
