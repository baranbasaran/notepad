package components;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class KeyStrokeActionManager {
    private JTextArea textArea;
    private UndoManager undoManager;
    private String previousText;

    public KeyStrokeActionManager(JTextArea textArea, UndoManager undoManager, String previousText) {
        this.textArea = textArea;
        this.undoManager = undoManager;
        this.previousText = previousText;
    }

    public void setupKeyStrokeActions() {
        setupUndoAction();
        setupRedoAction();
        setupRemoveAction();
        setupDeleteAction();
        setupCutAction();
        setupCopyAction();
        setupPasteAction();
    }

    private void setupUndoAction() {
        KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK);
        textArea.getInputMap().put(undoKeyStroke, "undoKeyStroke");
        textArea.getActionMap().put("undoKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                undoManager.undo();
            }
        });
    }

    private void setupRedoAction() {
        KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK);
        textArea.getInputMap().put(redoKeyStroke, "redoKeyStroke");
        textArea.getActionMap().put("redoKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                undoManager.redo();
            }
        });
    }

    private void setupRemoveAction() {
        KeyStroke removeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0);
        textArea.getInputMap().put(removeKeyStroke, "removeKeyStroke");
        textArea.getActionMap().put("removeKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                undoManager.addUndo(previousText);
                if (textArea.getSelectionStart() == textArea.getSelectionEnd()) {
                    int caretPosition = textArea.getCaretPosition();
                    if (caretPosition > 0) {
                        textArea.replaceRange("", caretPosition - 1, caretPosition);
                    }
                } else {
                    textArea.replaceSelection("");
                }
            }
        });
    }

    private void setupDeleteAction() {
        KeyStroke deleteKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
        textArea.getInputMap().put(deleteKeyStroke, "deleteKeyStroke");
        textArea.getActionMap().put("deleteKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                undoManager.addUndo(previousText);
                if (textArea.getSelectionStart() == textArea.getSelectionEnd()) {
                    int caretPosition = textArea.getCaretPosition();
                    if (caretPosition < textArea.getDocument().getLength()) {
                        textArea.replaceRange("", caretPosition, caretPosition + 1);
                    }
                } else {
                    textArea.replaceSelection("");
                }
            }
        });
    }

    private void setupCutAction() {
        KeyStroke cutKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK);
        textArea.getInputMap().put(cutKeyStroke, "cutKeyStroke");
        textArea.getActionMap().put("cutKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                undoManager.addUndo(previousText);
                textArea.cut();

            }
        });
    }

    private void setupCopyAction() {
        KeyStroke copyKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK);
        textArea.getInputMap().put(copyKeyStroke, "copyKeyStroke");
        textArea.getActionMap().put("copyKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                undoManager.addUndo(previousText);
                textArea.copy();

            }
        });
    }

    private void setupPasteAction() {
        KeyStroke pasteKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK);
        textArea.getInputMap().put(pasteKeyStroke, "pasteKeyStroke");
        textArea.getActionMap().put("pasteKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                undoManager.addUndo(previousText);
                textArea.paste();
            }
        });
    }
}