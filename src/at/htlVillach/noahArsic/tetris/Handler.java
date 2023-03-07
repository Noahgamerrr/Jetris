package at.htlVillach.noahArsic.tetris;

import java.awt.*;
import java.util.LinkedList;

public class Handler {
    public LinkedList<GameObject> object = new LinkedList<>();

    public void tick() {
        for (GameObject gameObject : object) {
            gameObject.tick();
        }
    }

    public void render(Graphics g) {
        for (GameObject gameObject : object) {
            gameObject.render(g);
        }
    }

    public void addObject(GameObject temp) {
        object.add(temp);
    }

    public void removeObject(GameObject temp) {
        object.remove(temp);
    }

    public void clearHandler() {
        if (object.size() > 0) {
            object.subList(0, object.size()).clear();
        }
    }
}
