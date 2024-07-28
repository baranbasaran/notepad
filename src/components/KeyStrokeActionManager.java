package components;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class KeyStrokeActionManager {
    private JTextArea textArea;
    private UndoManager undoManager;
    private String previousText;

    private static final KeyStroke UNDO_KEY_STROKE = KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK);
    private static final KeyStroke REDO_KEY_STROKE = KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK);
    private static final KeyStroke REMOVE_KEY_STROKE = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0);
    private static final KeyStroke DELETE_KEY_STROKE = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
    private static final KeyStroke CUT_KEY_STROKE = KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK);
    private static final KeyStroke COPY_KEY_STROKE = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK);
    private static final KeyStroke PASTE_KEY_STROKE = KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK);

    public KeyStrokeActionManager(JTextArea textArea, UndoManager undoManager, String previousText) {
        this.textArea = textArea;
        this.undoManager = undoManager;
        this.previousText = previousText;
    }

    public void setupKeyStrokeActions() {
        setupAction(UNDO_KEY_STROKE, "undoKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoManager.undo();
            }
        });
        setupAction(REDO_KEY_STROKE, "redoKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoManager.redo();
            }
        });
        setupAction(REMOVE_KEY_STROKE, "removeKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRemoveAction();
            }
        });
        setupAction(DELETE_KEY_STROKE, "deleteKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteAction();
            }
        });
        setupAction(CUT_KEY_STROKE, "cutKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCutAction();
            }
        });
        setupAction(COPY_KEY_STROKE, "copyKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCopyAction();
            }
        });
        setupAction(PASTE_KEY_STROKE, "pasteKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePasteAction();
            }
        });
    }

    private void setupAction(KeyStroke keyStroke, String actionName, AbstractAction action) {
        textArea.getInputMap().put(keyStroke, actionName);
        textArea.getActionMap().put(actionName, action);
    }

    private void handleRemoveAction() {
        updatePreviousText();
        if (textArea.getSelectionStart() == textArea.getSelectionEnd()) {
            int caretPosition = textArea.getCaretPosition();
            if (caretPosition > 0) {
                textArea.replaceRange("", caretPosition - 1, caretPosition);
            }
        } else {
            textArea.replaceSelection("");
        }
    }

    private void handleDeleteAction() {
        updatePreviousText();
        if (textArea.getSelectionStart() == textArea.getSelectionEnd()) {
            int caretPosition = textArea.getCaretPosition();
            if (caretPosition < textArea.getDocument().getLength()) {
                textArea.replaceRange("", caretPosition, caretPosition + 1);
            }
        } else {
            textArea.replaceSelection("");
        }
    }

    private void handleCutAction() {
        updatePreviousText();
        textArea.cut();
    }

    private void handleCopyAction() {
        textArea.copy();
    }

    private void handlePasteAction() {
        updatePreviousText();
        textArea.paste();
    }

    private void updatePreviousText() {
        previousText = textArea.getText();
        undoManager.addUndo(previousText);
    }
}
