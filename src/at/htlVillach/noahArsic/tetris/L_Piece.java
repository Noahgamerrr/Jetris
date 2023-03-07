package at.htlVillach.noahArsic.tetris;

import java.awt.*;

public class L_Piece extends GameObject {

    public L_Piece(Game game) {
        super(3, game);
        pieceHeight = 2;
        pieceWidth = 3;
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
                    while (i < 3 && !moveUpwards) {
                        if (FieldHandler.getField()[realX + i][realY + 1] != 0) moveUpwards = true;
                        i++;
                    }
                    if (FieldHandler.getField()[realX + 2][realY] != 0) moveUpwards = true;
                }
                case 1 -> {
                    while (i < 3 && !moveUpwards) {
                        if (FieldHandler.getField()[realX + 1][realY + i] != 0) moveUpwards = true;
                        i++;
                    }
                    if (FieldHandler.getField()[realX + 2][realY + 2] != 0) moveUpwards = true;
                }
                case 2 -> {
                    while (i < 3 && !moveUpwards) {
                        if (FieldHandler.getField()[realX + i][realY + 1] != 0) moveUpwards = true;
                        i++;
                    }
                    if (FieldHandler.getField()[realX][realY + 2] != 0) moveUpwards = true;
                }
                case 3 -> {
                    while (i < 3 && !moveUpwards) {
                        if (FieldHandler.getField()[realX + 1][realY + i] != 0) moveUpwards = true;
                        i++;
                    }
                    if (FieldHandler.getField()[realX][realY] != 0) moveUpwards = true;
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
            case 0 -> {
                if (FieldHandler.getField()[realX + 1][realY] != 0) pieceToLeft = true;
                if (FieldHandler.getField()[realX - 1][realY + 1] != 0) pieceToLeft = true;
            }
            case 1 -> {
                while (!pieceToLeft && i < 3) {
                    if (FieldHandler.getField()[realX - 1][realY + i] != 0) pieceToLeft = true;
                    i++;
                }
            }
            case 2 -> {
                while (!pieceToLeft && i < 2) {
                    if (FieldHandler.getField()[realX - 1][realY + i] != 0) pieceToLeft = true;
                    i++;
                }
            }
            case 3 -> {
                i = 1;
                if (FieldHandler.getField()[realX - 1][realY] != 0) pieceToLeft = true;
                while (!pieceToLeft && i < 3) {
                    if (FieldHandler.getField()[realX][realY + i] != 0) pieceToLeft = true;
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
            case 0 -> {
                while (!pieceToRight && i < 2) {
                    if (FieldHandler.getField()[realX + pieceWidth][realY + i] != 0) pieceToRight = true;
                    i++;
                }
            }
            case 1 -> {
                while (!pieceToRight && i < 2) {
                    if (FieldHandler.getField()[realX + 1][realY + i] != 0) pieceToRight = true;
                    i++;
                }
                if (FieldHandler.getField()[realX + pieceWidth][realY + 2] != 0) pieceToRight = true;
            }
            case 2 -> {
                if (FieldHandler.getField()[realX + pieceWidth][realY] != 0) pieceToRight = true;
                if (FieldHandler.getField()[realX + 1][realY + 1] != 0) pieceToRight = true;
            }
            case 3 -> {
                while (!pieceToRight && i < 3) {
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
                case 0 -> {
                    while (!pieceBeneath && i < 3) {
                        if (FieldHandler.getField()[realX + i][realY + pieceHeight] != 0) pieceBeneath = true;
                        i++;
                    }
                }
                case 1 -> {
                    while (!pieceBeneath && i < 2) {
                        if (FieldHandler.getField()[realX + i][realY + pieceHeight] != 0) pieceBeneath = true;
                        i++;
                    }
                }
                case 2 -> {
                    if (FieldHandler.getField()[realX][realY + pieceHeight] != 0) pieceBeneath = true;
                    while (!pieceBeneath && i < 3) {
                        if (FieldHandler.getField()[realX + i][realY + 1] != 0) pieceBeneath = true;
                        i++;
                    }
                }
                case 3 -> {
                    if (FieldHandler.getField()[realX][realY + 1] != 0) pieceBeneath = true;
                    if (FieldHandler.getField()[realX + 1] [realY + pieceHeight] != 0) pieceBeneath = true;
                }
            }
        }
        return pieceBeneath;
    }

    @Override
    public void placePieceInField() {
        switch (rotation) {
            case 0 -> {
                for (int i = x; i < x + 3; i++) FieldHandler.setPlace(i, y + 1, intId);
                FieldHandler.setPlace(x + 2, y, intId);
            }
            case 1 -> {
                for (int i = y; i < y + 3; i++) FieldHandler.setPlace(x + 1, i, intId);
                FieldHandler.setPlace(x + 2, y + 2, intId);
            }
            case 2 -> {
                for (int i = x; i < x + 3; i++) FieldHandler.setPlace(i, y + 1, intId);
                FieldHandler.setPlace(x, y + 2, intId);
            }
            case 3 -> {
                for (int i = y; i < y + 3; i++) FieldHandler.setPlace(x + 1, i, intId);
                FieldHandler.setPlace(x , y, intId);
            }
        }
        Game.getHandler().removeObject(this);
        Game.getHud().addToScore(30);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.orange);
        realX = rotation == 1 ? x + 1 : x;
        realY = rotation == 2 ? y + 1 : y;
        pieceWidth = rotation % 2 == 0 ? 3 : 2;
        pieceHeight = rotation % 2 == 0 ? 2 : 3;
        switch (rotation) {
            case 0 -> {
                if (realX + pieceWidth > FieldHandler.getFieldWidth()) x = FieldHandler.getFieldWidth() - pieceWidth;
                for (int i = x; i < x + 3; i++) {
                    g.fillRect(FieldHandler.getBlockSide() * i + i + 200, FieldHandler.getBlockSide() * (y + 1) + y + 51,
                            FieldHandler.getBlockSide(), FieldHandler.getBlockSide());
                }
                g.fillRect(FieldHandler.getBlockSide() * (x + 2) + x + 202, FieldHandler.getBlockSide() * y + y + 50,
                        FieldHandler.getBlockSide(), FieldHandler.getBlockSide());
            }
            case 1 -> {
                for (int i = y; i < y + 3; i++) {
                    g.fillRect(FieldHandler.getBlockSide() * (x + 1) + x + 201, FieldHandler.getBlockSide() * i + i + 50,
                            FieldHandler.getBlockSide(), FieldHandler.getBlockSide());
                }
                g.fillRect(FieldHandler.getBlockSide() * (x + 2) + x + 202, FieldHandler.getBlockSide() * (y + 2) + y + 52,
                        FieldHandler.getBlockSide(), FieldHandler.getBlockSide());
            }
            case 2 -> {
                if (realX < 0) x = 0;
                for (int i = x; i < x + 3; i++) {
                    g.fillRect(FieldHandler.getBlockSide() * i + i + 200, FieldHandler.getBlockSide() * (y + 1) + y + 51,
                            FieldHandler.getBlockSide(), FieldHandler.getBlockSide());
                }
                g.fillRect(FieldHandler.getBlockSide() * x + x + 200, FieldHandler.getBlockSide() * (y + 2) + y + 52,
                        FieldHandler.getBlockSide(), FieldHandler.getBlockSide());
            }
            case 3 -> {
                for (int i = y; i < y + 3; i++) {
                    g.fillRect(FieldHandler.getBlockSide() * (x + 1) + x + 201, FieldHandler.getBlockSide() * i + i + 50,
                            FieldHandler.getBlockSide(), FieldHandler.getBlockSide());
                }
                g.fillRect(FieldHandler.getBlockSide() * x + x + 200, FieldHandler.getBlockSide() * y + y + 50,
                        FieldHandler.getBlockSide(), FieldHandler.getBlockSide());
            }
        }
    }
}
