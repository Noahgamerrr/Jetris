package at.htlVillach.noahArsic.tetris;

import java.awt.*;

public class FieldHandler {
    private static final int FIELD_HEIGHT = 20, FIELD_WIDTH = 10;
    private static final int BLOCK_SIDE = 30;
    private static final int[][] field = new int[FIELD_WIDTH][FIELD_HEIGHT];
    private static int combo = 1;

    public void initializeArray() {
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_HEIGHT; j++) {
                field[i][j] = 0;
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, Game.getGameWidth(), Game.getGameHeight());
        g.setColor(Color.black);
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_HEIGHT; j++) {
                switch (field[i][j]) {
                    case 1 -> g.setColor(Color.cyan);
                    case 2 -> g.setColor(Color.blue);
                    case 3 -> g.setColor(Color.orange);
                    case 4 -> g.setColor(Color.yellow);
                    case 5 -> g.setColor(Color.green);
                    case 6 -> g.setColor(Color.magenta);
                    case 7 -> g.setColor(Color.red);
                    default -> g.setColor(Color.black);
                }
                g.fillRect( BLOCK_SIDE * i + i + 200, BLOCK_SIDE * j + j + 50, BLOCK_SIDE, BLOCK_SIDE);
            }
        }
    }

    public static void checkForFullRows() {
        boolean fullRow;
        boolean[] fullRows = new boolean[FIELD_HEIGHT];
        int fullRowsCount = 0;
        for (int i = FIELD_HEIGHT - 1; i >= 0; i--) {
            fullRow = true;
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (field[j][i] == 0) {
                    fullRow = false;
                    break;
                }
            }
            if (fullRow) fullRowsCount++;
            fullRows[i] = fullRow;
        }
        deleteFullRows(fullRows);
        moveRowsDown(fullRows);
        Game.getHud().addToScore(100 * fullRowsCount * combo);
        Game.getHud().addToLines(fullRowsCount);
        if (fullRowsCount != 0) combo++;
        else combo = 1;
    }

    private static void deleteFullRows(boolean[] fullRows) {
        for (int i = 0; i < fullRows.length; i++) {
            if (fullRows[i]) {
                for (int j = 0; j < FIELD_WIDTH; j++) {
                    field[j][i] = 0;
                }
            }
        }
    }

    private static void moveRowsDown(boolean[] fullRows) {
        for (int i = 0; i < FIELD_HEIGHT; i++) {
            if (fullRows[i]) {
                for (int j = i; j > 0; j--) {
                    for (int k = 0; k < FIELD_WIDTH; k++) {
                        field[k][j] = field[k][j - 1];
                    }
                }
            }
        }
    }

    public static int getFieldWidth() {
        return FIELD_WIDTH;
    }

    public static int getFieldHeight() {
        return FIELD_HEIGHT;
    }

    public static int getBlockSide() {
        return BLOCK_SIDE;
    }

    public static int[][] getField() {
        return field;
    }

    public static void setPlace(int i, int j, int value) {
        field[i][j] = value;
    }
}
