package at.htlVillach.noahArsic.tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    private final Handler handler;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_X) {
            handler.object.get(0).changeRotation();
            handler.object.get(0).movePieceUpwards();
        }
        if (e.getKeyCode() == KeyEvent.VK_A && handler.object.get(0).getRealX() > 0 && !handler.object.get(0).checkPiecesToLeft()) {
            handler.object.get(0).setRealX(handler.object.get(0).getRealX() - 1);
            handler.object.get(0).setX(handler.object.get(0).getX() - 1);
        }
        if (e.getKeyCode() == KeyEvent.VK_D && handler.object.get(0).getRealX() + handler.object.get(0).getPieceWidth() < FieldHandler.getFieldWidth()
                && !handler.object.get(0).checkPiecesToRight()) {
            handler.object.get(0).setRealX(handler.object.get(0).getRealX() + 1);
            handler.object.get(0).setX(handler.object.get(0).getX() + 1);
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            GameObject.setsPressed(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_C) {
            Game.getPg().savePiece();
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            while (handler.object.get(0).getAllowFall()) {
                handler.object.get(0).setY(handler.object.get(0).getY() + 1);
                handler.object.get(0).setRealY(handler.object.get(0).getRealY() + 1);
                handler.object.get(0).setAllowFall(!handler.object.get(0).checkIfPiecePlaced());
            }
            handler.object.get(0).placePieceInField();
            FieldHandler.checkForFullRows();
            Game.getPg().spawnPiece(Game.getPg().nextPiece());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S) GameObject.setsPressed(false);
    }
}
