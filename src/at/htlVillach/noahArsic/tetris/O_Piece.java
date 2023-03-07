package at.htlVillach.noahArsic.tetris;

import java.awt.*;

public class O_Piece extends GameObject {

    public O_Piece(Game game) {
        super(4, game);
        realX = x;
        pieceWidth = 2;
        pieceHeight = 2;
        checkForGameOver();
    }

    @Override
    public void movePieceUpwards() {
        boolean moveUpwards;
        int i = 0;
        int j = 0;
        do {
            moveUpwards = false;
            while (i < 2 && !moveUpwards) {
                while (j < 2 && !moveUpwards) {
                    if (FieldHandler.getField()[realX + i][realX + j] != 0) moveUpwards = true;
                    j++;
                }
                i++;
            }
            if (moveUpwards) {
                y--;
                realY--;
            }
        } while (moveUpwards);
    }

    @Override
    public boolean checkPiecesToLeft() {
        boolean pieceToLeft = false;
        int i = 0;
        while (!pieceToLeft && i < 2) {
            if (FieldHandler.getField()[realX - 1][realY + i] != 0) pieceToLeft = true;
            i++;
        }
        return pieceToLeft;
    }

    @Override
    public boolean checkPiecesToRight() {
        boolean pieceToRight = false;
        int i = 0;
        while (!pieceToRight && i < 2) {
            if (FieldHandler.getField()[realX + pieceWidth][realY + i] != 0) pieceToRight = true;
            i++;
        }
        return pieceToRight;
    }

    @Override
    protected boolean checkPiecesBeneath() {
        boolean pieceBeneath = false;
        int i = 0;
        if (realY + pieceHeight < 20) {
            while (!pieceBeneath && i < 2) {
                if (FieldHandler.getField()[realX + i][realY + pieceHeight] != 0) pieceBeneath = true;
                i++;
            }
        }
        return pieceBeneath;
    }

    @Override
    public void placePieceInField() {
        for (int i = x; i < x + 2; i++) {
            for (int j = y; j < y + 2; j++) FieldHandler.setPlace(i, j, intId);
        }
        Game.getHandler().removeObject(this);
        Game.getHud().addToScore(30);
    }

    @Override
    public void render(Graphics g) {
        if (y + pieceHeight == FieldHandler.getFieldHeight() + 1) y = FieldHandler.getFieldHeight() - pieceHeight;
        realY = y;
        g.setColor(Color.yellow);
        for (int i = x; i < x + 2; i++) {
            for (int j = y; j < y + 2; j++) {
                g.fillRect( FieldHandler.getBlockSide() * i + i + 200,  FieldHandler.getBlockSide() * j + j + 50,
                        FieldHandler.getBlockSide(), FieldHandler.getBlockSide());
            }
        }
    }
}
