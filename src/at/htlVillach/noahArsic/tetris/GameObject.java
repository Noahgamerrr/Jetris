package at.htlVillach.noahArsic.tetris;

import java.awt.*;

public abstract class GameObject {
    private static long time = System.currentTimeMillis();
    private static int loop = 0;
    private static int timeOut = 1000;
    private static boolean sPressed = false;
    private final Game game;
    private static final Handler handler = Game.getHandler();
    protected boolean allowFall = true;
    protected static final int startX = 4, startY = 0;
    protected int x, y;
    protected int rotation = 0;
    protected int realX, realY;
    protected int pieceWidth, pieceHeight;
    protected int intId;

    public GameObject(int intId, Game game) {
        this.x = startX;
        this.y = startY;
        this.game = game;
        this.intId = intId;
    }

    protected abstract boolean checkPiecesBeneath();
    public abstract void placePieceInField();
    public abstract boolean checkPiecesToLeft();
    public abstract boolean checkPiecesToRight();
    public abstract void render(Graphics g);
    public abstract void movePieceUpwards();

    public void tick() {
        long timeNow = System.currentTimeMillis();
        if (realY + pieceHeight == FieldHandler.getFieldHeight() + 1) {
            y = FieldHandler.getFieldHeight() - pieceHeight;
            realY = y;
        }
        if (realY + pieceHeight == FieldHandler.getFieldHeight() || checkIfPiecePlaced()) allowFall = false;
        if (!checkIfPiecePlaced()) allowFall = true;
        if (allowFall) {
            if (sPressed) {
                if (System.currentTimeMillis() - time >= 100) {
                    y++;
                    realY++;
                    time = System.currentTimeMillis();
                }
            } else {
                if (timeNow - time > timeOut) {
                    time = timeNow;
                    y++;
                    realY++;
                }
            }
        }
        else if (waitSecond()) {
            placePieceInField();
            FieldHandler.checkForFullRows();
            Game.getPg().spawnPiece(Game.getPg().nextPiece());
        }
    }

    protected void checkForGameOver() {
        boolean gameOver = false;
        for (int i = 0; i <= pieceWidth; i++) {
            for (int j = 0; j <= pieceHeight; j++) {
                if (FieldHandler.getField()[realX + i][realY + j] != 0) {
                    gameOver = true;
                    break;
                }
            }
        }
        if (gameOver) {
            game.addMouseListener(Game.getGameOver());
            game.removeKeyListener(Game.getKeyInput());
            handler.clearHandler();
            Game.setGameState(Game.GAME_STATE.GameOver);
        }
    }

    private boolean waitSecond() {
        boolean placePiece = false;
        if (realY + pieceHeight == FieldHandler.getFieldHeight() || checkPiecesBeneath()) {
            loop++;
            if (loop == 50) {
                loop = 0;
                placePiece =  true;
            }
        }
        if (allowFall) loop = 0;
        return placePiece;
    }

    public static void shortenTimeOut() {
        timeOut -= 80;
    }

    public boolean checkIfPiecePlaced() {
        return realY + pieceHeight == FieldHandler.getFieldHeight() || checkPiecesBeneath();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRealX() {
        return realX;
    }

    public int getRealY() {
        return realY;
    }

    public void setRealX(int realX) {
        this.realX = realX;
    }

    public void setRealY(int realY) {
        this.realY = realY;
    }

    public int getIntId() {
        return intId;
    }

    public int getPieceWidth() {
        return pieceWidth;
    }

    public void setAllowFall(boolean allowFall) {
        this.allowFall = allowFall;
    }

    public boolean getAllowFall() {
        return allowFall;
    }

    public void changeRotation() {
        rotation = (rotation + 1) % 4;
    }

    public static void setsPressed(boolean sPressed) {
        GameObject.sPressed = sPressed;
    }
}
