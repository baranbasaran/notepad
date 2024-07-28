package components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Utilities;
import java.awt.*;

class LineNumberHeaderView extends JComponent implements DocumentListener {
    private static final long serialVersionUID = 1L;
    private final JTextArea textArea;
    private final FontMetrics fontMetrics;
    private final int fontHeight;
    private final int fontAscent;

    public LineNumberHeaderView(JTextArea textArea) {
        this.textArea = textArea;
        fontMetrics = textArea.getFontMetrics(textArea.getFont());
        fontHeight = fontMetrics.getHeight();
        fontAscent = fontMetrics.getAscent();

        textArea.getDocument().addDocumentListener(this);
        setPreferredSize(new Dimension(25, textArea.getHeight()));
        setBackground(new Color(60, 63, 65));
        setForeground(new Color(128, 128, 128));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(getForeground());

        Rectangle clip = g.getClipBounds();
        int startOffset = textArea.viewToModel2D(new Point(0, clip.y));
        int endOffset = textArea.viewToModel2D(new Point(0, clip.y + clip.height));
        int documentLength = textArea.getDocument().getLength();

        while (startOffset <= endOffset && startOffset <= documentLength) {
            try {
                Rectangle rect = textArea.modelToView2D(startOffset).getBounds();
                int lineNumber = getLineNumber(startOffset);
                String lineNumberStr = String.valueOf(lineNumber);

                int x = getWidth() - fontMetrics.stringWidth(lineNumberStr) - 5;
                int y = rect.y + fontAscent;

                g.drawString(lineNumberStr, x, y);
                startOffset = Utilities.getRowEnd(textArea, startOffset) + 1;
            } catch (BadLocationException e) {
                break;
            }
        }
    }

    private int getLineNumber(int offset) {
        Element root = textArea.getDocument().getDefaultRootElement();
        return root.getElementIndex(offset) + 1;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        repaint();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        repaint();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        repaint();
    }
}
