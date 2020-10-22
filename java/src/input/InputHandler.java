package input;

import observer.Subject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    private Subject<InputKey> subject = new Subject<>();

    public Subject<InputKey> getSubject() {
        return subject;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keycode = keyEvent.getKeyCode();
        if(keycode == KeyEvent.VK_UP || keycode == KeyEvent.VK_W) {
            subject.setState(InputKey.UP);
        }
        if(keycode == KeyEvent.VK_DOWN || keycode == KeyEvent.VK_S) {
            subject.setState(InputKey.DOWN);
        }
        if(keycode == KeyEvent.VK_ENTER) {
            subject.setState(InputKey.ENTER);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
