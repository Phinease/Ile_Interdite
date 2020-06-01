package control;

import modele.Modele;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyControl implements KeyListener {
    protected Modele modele;

    public KeyControl(Modele modele){
        this.modele = modele;
    }

    public enum Direction {
        up, down, right, left, mid
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                this.modele.move(Direction.up);
                break;
            case KeyEvent.VK_DOWN:
                this.modele.move(Direction.down);
                break;
            case KeyEvent.VK_LEFT:
                this.modele.move(Direction.left);
                break;
            case KeyEvent.VK_RIGHT:
                this.modele.move(Direction.right);
                break;
        }
        modele.notifyObservers();
    }

    @Override
    public void keyReleased(KeyEvent e) { }
    public void keyTyped(KeyEvent e) { }
}
