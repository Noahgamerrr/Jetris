package at.htlVillach.noahArsic.tetris;

import java.awt.*;

public class HUD {
    private int frames;
    private int score;
    private int lines;
    private int level;

    public void render(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(509,400, 150, 269);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("FPS: " +frames, 10, 20);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: ", 515, 430);
        g.drawString("Lines: ", 515, 520);
        g.drawString("Level: ", 515, 610);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString(String.valueOf(score), 570, 430);
        g.drawString(String.valueOf(lines), 570, 520);
        g.drawString(String.valueOf(level), 570, 610);
    }

    public void addToScore(int value) {
        score += value;
    }

    public void addToLines(int value) {
        lines += value;
        changeLevel();
    }

    private void changeLevel() {
        int levelBefore = level;
        level = lines / 10;
        if (levelBefore != level) GameObject.shortenTimeOut();
    }

    public int getScore() {
        return score;
    }

    public void setFrames(int frames) {
        this.frames = frames;
    }
}
