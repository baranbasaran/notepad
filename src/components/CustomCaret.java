package components;
import javax.swing.text.DefaultCaret;

public class CustomCaret {
    private DefaultCaret caret;

    public CustomCaret() {
        initCaret();
    }

    private void initCaret() {
        caret = new DefaultCaret() {
            @Override
            protected synchronized void damage(java.awt.Rectangle r) {
                if (r == null) return;
                x = r.x;
                y = r.y;
                height = r.height;
                width = 2;
                repaint();
            }
        };
        caret.setBlinkRate(500);
    }

    public DefaultCaret getCaret() {
        return caret;
    }
}
