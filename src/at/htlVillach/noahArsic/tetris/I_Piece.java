package at.htlVillach.noahArsic.tetris;

import java.awt.*;

public class I_Piece extends GameObject {

    public I_Piece(Game game) {
        super(1, game);
        pieceHeight = 1;
        pieceWidth = 4;
        checkForGameOver();
    }

    @Override
    public void movePieceUpwards() {
        boolean moveUpwards;
        int i = 0;
        do {
            moveUpwards = false;
            switch (rotation) {
                case 0 -> {
                    while (i < 4 && !moveUpwards) {
                        if (FieldHandler.getField()[i + realX][realY + 1] != 0) moveUpwards = true;
                        i++;
                    }
                }
                case 1 -> {
                    while (i < 4 && !moveUpwards) {
                        if (FieldHandler.getField()[realX + 2][realY + i] != 0) moveUpwards = true;
                        i++;
                    }
                }
                case 2 -> {
                    while (i < 4 && !moveUpwards) {
                        if (FieldHandler.getField()[realX + i][realY + 2] != 0) moveUpwards = true;
                        i++;
                    }
                }
                case 3 -> {
                    while (i < 4 && !moveUpwards) {
                        if (FieldHandler.getField()[realX + 1][realY + i] != 0) moveUpwards = true;
                        i++;
                    }
                }
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
        switch (rotation) {
            case 0, 2 -> {
                if (FieldHandler.getField()[realX - 1][realY] != 0) pieceToLeft = true;
            }
            case 1, 3 -> {
                while (!pieceToLeft && i < 4) {
                    if (FieldHandler.getField()[realX - 1][realY + i] != 0) pieceToLeft = true;
                    i++;
                }
            }
        }
        return pieceToLeft;
    }

    @Override
    public boolean checkPiecesToRight() {
        boolean pieceToRight = false;
        int i = 0;
        switch (rotation) {
            case 0, 2 -> {
                if (FieldHandler.getField()[realX + pieceWidth][realY] != 0) pieceToRight = true;
            }
            case 1, 3 -> {
                while (!pieceToRight && i < 4) {
                    if (FieldHandler.getField()[realX + pieceWidth][realY + i] != 0) pieceToRight = true;
                    i++;
                }
            }
        }
        return pieceToRight;
    }

    @Override
    protected boolean checkPiecesBeneath() {
        boolean pieceBeneath = false;
        int i = 0;
        if (realY + pieceHeight < 20) {
            switch (rotation) {
                case 0, 2 -> {
                    while (!pieceBeneath && i < 4) {
                        if (FieldHandler.getField()[realX + i][realY + 1] != 0) {
                            pieceBeneath = true;
                        }
                        i++;
                    }
                }
                case 1, 3 -> {
                    if (FieldHandler.getField()[realX][realY + pieceHeight] != 0) pieceBeneath = true;
                }
            }
        }
        return pieceBeneath;
    }

    @Override
    public void placePieceInField() {
        switch (rotation) {
            case 0 -> {
                for (int i = x; i < x + 4; i++) FieldHandler.setPlace(i, y + 1, intId);
            }
            case 1 -> {
                for (int i = y; i < y + 4; i++) FieldHandler.setPlace(x + 2, i, intId);
            }
            case 2 -> {
                for (int i = x; i < x + 4; i++) FieldHandler.setPlace(i, y + 2, intId);
            }
            case 3 -> {
                for (int i = y; i < y + 4; i++) FieldHandler.setPlace(x + 1, i, intId);
            }
        }
        Game.getHandler().removeObject(this);
        Game.getHud().addToScore(30);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.cyan);
        pieceWidth = rotation % 2 == 0 ? 4 : 1;
        pieceHeight = rotation % 2 == 0 ? 1 : 4;
        switch (rotation) {
            case 0 -> {
                realX = x;
                realY = y + 1;
                if (realX < 0) x = 0;
                else if (realX + pieceWidth > FieldHandler.getFieldWidth()) x = FieldHandler.getFieldWidth() - pieceWidth;
                for (int i = x; i < x + 4; i++) {
                    g.fillRect( FieldHandler.getBlockSide() * i + i + 200,  FieldHandler.getBlockSide() * (y + 1) + y + 51,
                            FieldHandler.getBlockSide(), FieldHandler.getBlockSide());
                }
            }
            case 1 -> {
                realX = x + 2;
                realY = y;
                for (int i = y; i < y + 4; i++) {
                    g.fillRect(FieldHandler.getBlockSide() * (x + 2) + x + 202, FieldHandler.getBlockSide() * i + i + 50,
                            FieldHandler.getBlockSide(), FieldHandler.getBlockSide());
                }
            }
            case 2 -> {
                realX = x;
                realY = y + 2;
                if (realX < 0) x = 0;
                else if (realX + pieceWidth > FieldHandler.getFieldWidth()) x = FieldHandler.getFieldWidth() - pieceWidth;
                for (int i = x; i < x + 4; i++) {
                    g.fillRect( FieldHandler.getBlockSide() * i + i + 200,  FieldHandler.getBlockSide() * (y + 2) + y + 52,
                            FieldHandler.getBlockSide(), FieldHandler.getBlockSide());
                }
            }
            case 3 -> {
                realX = x + 1;
                realY = y;
                for (int i = y; i < y + 4; i++) {
                    g.fillRect(FieldHandler.getBlockSide() * (x + 1) + x + 201, FieldHandler.getBlockSide() * i + i + 50,
                            FieldHandler.getBlockSide(), FieldHandler.getBlockSide());
                }
            }
        }
    }
}
