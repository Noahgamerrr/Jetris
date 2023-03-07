package at.htlVillach.noahArsic.tetris;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public record PieceGenerator (Game game) {
    private static final int[] PIECES = {1, 2, 3, 4, 5, 6, 7};
    private static final int[] pieces = new int[14];
    private static int savedPiece;
    private static int indexCounter = 0;
    private static boolean wasSaved = false;

    public PieceGenerator {
        generatePieces(0);
        spawnPiece(nextPiece());
    }

    public void initialize() {
        indexCounter = 0;
        savedPiece = 0;
        Arrays.fill(pieces, 0);
        generatePieces(0);
        spawnPiece(nextPiece());
    }

    private int[] shuffle() {
        Random r = new Random();
        int[] piecesCopy = PIECES.clone();
        int i1, i2;
        int help;
        for (int i = 0; i < 50; i++) {
            i1 = r.nextInt(7);
            do {
                i2 = r.nextInt(7);
            } while (i2 == i1);
            help = piecesCopy[i1];
            piecesCopy[i1] = piecesCopy[i2];
            piecesCopy[i2] = help;
        }
        return piecesCopy;
    }

    public int nextPiece() {
        if (indexCounter == 2) generatePieces(2);
        else if (indexCounter == 8) generatePieces(1);
        else if (indexCounter == 14) indexCounter = 0;
        indexCounter++;
        wasSaved = false;
        return pieces[indexCounter - 1];
    }

    private void generatePieces(int mode) {
        int[] allPieces;
        if (mode != 2) {
            allPieces = shuffle();
            for (int i = 0; i < 7; i++) {
                setPiece(i, allPieces[i]);
            }
        }
        if (mode != 1) {
            allPieces = shuffle();
            for (int i = 0; i < 7; i++) {
                setPiece(i + 7, allPieces[i]);
            }
        }
    }

    public void spawnPiece(int piece) {
        switch (piece) {
            case 1 -> Game.getHandler().addObject(new I_Piece(game));
            case 2 -> Game.getHandler().addObject(new J_Piece(game));
            case 3 -> Game.getHandler().addObject(new L_Piece(game));
            case 4 -> Game.getHandler().addObject(new O_Piece(game));
            case 5 -> Game.getHandler().addObject(new S_Piece(game));
            case 6 -> Game.getHandler().addObject(new T_Piece(game));
            case 7 -> Game.getHandler().addObject(new Z_Piece(game));
        }
    }

    public void savePiece() {
        if (!wasSaved) {
            if (savedPiece == 0) {
                savedPiece = Game.getHandler().object.get(0).getIntId();
                Game.getHandler().removeObject(Game.getHandler().object.get(0));
                spawnPiece(nextPiece());
            } else {
                int help = savedPiece;
                savedPiece = Game.getHandler().object.get(0).getIntId();
                Game.getHandler().removeObject(Game.getHandler().object.get(0));
                spawnPiece(help);
            }
        }
        wasSaved = true;
    }

    public void render(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(100, 50, 100, 250);
        g.fillRect(509, 50, 100, 100);
        renderIcon(g, pieces[indexCounter % 14], 0);
        renderIcon(g, pieces[(indexCounter + 1) % 14], 1);
        renderIcon(g, pieces[(indexCounter + 2) % 14], 2);
        renderIcon(g, savedPiece);
    }

    private void renderIcon(Graphics g, int piece) {
        switch (piece) {
            case 1 -> {
                g.setColor(Color.CYAN);
                for (int i = 0; i < 4; i++) {
                    g.fillRect(20 * i + i + 509, 90, 20, 20);
                }
            }
            case 2 -> {
                g.setColor(Color.BLUE);
                for (int i = 0; i < 3; i++) {
                    g.fillRect(20 * i + i + 509, 111, 20, 20);
                }
                g.fillRect(509, 90, 20, 20);
            }
            case 3 -> {
                g.setColor(Color.ORANGE);
                for (int i = 0; i < 3; i++) {
                    g.fillRect(20 * i + i + 509, 111, 20, 20);
                }
                g.fillRect(551, 90, 20, 20);
            }
            case 4 -> {
                g.setColor(Color.YELLOW);
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        g.fillRect(20 * i + i + 524, (21 * j) + 90, 20, 20);
                    }
                }
            }
            case 5 -> {
                g.setColor(Color.GREEN);
                for (int i = 1; i < 3; i++) {
                    g.fillRect(20 * i + i + 509, 90, 20, 20);
                }
                for (int i = 0; i < 2; i++) {
                    g.fillRect(20 * i + i + 509, 111, 20, 20);
                }
            }
            case 6 -> {
                g.setColor(Color.MAGENTA);
                g.fillRect(530, 90, 20, 20);
                for (int i = 0; i < 3; i++) {
                    g.fillRect(20 * i + i + 509, 111, 20, 20);
                }
            }
            case 7 -> {
                g.setColor(Color.RED);
                for (int i = 0; i < 2; i++) {
                    g.fillRect(20 * i + i + 509, 90, 20, 20);
                }
                for (int i = 1; i < 3; i++) {
                    g.fillRect(20 * i + i + 509, 111, 20, 20);
                }
            }
        }
    }

    private void renderIcon(Graphics g, int piece, int pos) {
        switch (piece) {
            case 1 -> {
                g.setColor(Color.CYAN);
                for (int i = 0; i < 4; i++) {
                    g.fillRect(20 * i + i + 110, 70 * (3 - pos) - pos + 20, 20, 20);
                }
            }
            case 2 -> {
                g.setColor(Color.BLUE);
                for (int i = 0; i < 3; i++) {
                    g.fillRect(20 * i + i + 110, 70 * (3 - pos) + pos + 41, 20, 20);
                }
                g.fillRect(110, 70 * (3 - pos) + pos + 20, 20, 20);
            }
            case 3 -> {
                g.setColor(Color.ORANGE);
                for (int i = 0; i < 3; i++) {
                    g.fillRect(20 * i + i + 110, 70 * (3 - pos) + pos + 41, 20, 20);
                }
                g.fillRect(20 * 2 + 2 + 110, 70 * (3 - pos) + pos + 20, 20, 20);
            }
            case 4 -> {
                g.setColor(Color.YELLOW);
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        g.fillRect(20 * i + i + 125, 70 * (3 - pos) + (21 * j) + pos + 20, 20, 20);
                    }
                }
            }
            case 5 -> {
                g.setColor(Color.GREEN);
                for (int i = 1; i < 3; i++) {
                    g.fillRect(20 * i + i + 110, 70 * (3 - pos) + pos + 20, 20, 20);
                }
                for (int i = 0; i < 2; i++) {
                    g.fillRect(20 * i + i + 110, 70 * (3 - pos) + pos + 41, 20, 20);
                }
            }
            case 6 -> {
                g.setColor(Color.MAGENTA);
                g.fillRect(131, 70 * (3 - pos) + pos + 20, 20, 20);
                for (int i = 0; i < 3; i++) {
                    g.fillRect(20 * i + i + 110, 70 * (3 - pos) + pos + 41, 20, 20);
                }
            }
            case 7 -> {
                g.setColor(Color.RED);
                for (int i = 0; i < 2; i++) {
                    g.fillRect(20 * i + i + 110, 70 * (3 - pos) + pos + 20, 20, 20);
                }
                for (int i = 1; i < 3; i++) {
                    g.fillRect(20 * i + i + 110, 70 * (3 - pos) + pos + 41, 20, 20);
                }
            }
        }
    }

    public void setPiece(int index, int value) {
        pieces[index] = value;
    }
}
