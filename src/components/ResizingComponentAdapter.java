package components;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class ResizingComponentAdapter extends ComponentAdapter {
    private JTextArea textArea;
    private JFrame frame;



    public ResizingComponentAdapter(JFrame frame, JTextArea textArea) {
        this.frame = frame;
        this.textArea = textArea;
    }

    @Override
    public void componentResized(ComponentEvent componentEvent) {
        Dimension size = frame.getSize();
        textArea.setBounds(50 , 0, size.width - 20, size.height - 20);

    }
}