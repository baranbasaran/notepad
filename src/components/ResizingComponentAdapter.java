package components;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class ResizingComponentAdapter extends ComponentAdapter {
    private JTextArea textArea;
    private JFrame frame;



    public ResizingComponentAdapter(JFrame frame, JTextArea textArea,JToolBar toolBar) {
        this.frame = frame;
        this.textArea = textArea;
    }

    @Override
    public void componentResized(ComponentEvent componentEvent) {
        Dimension size = frame.getSize();
        textArea.setBounds(10, 10, size.width - 20, size.height - 20);

    }
}