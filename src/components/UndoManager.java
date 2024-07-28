package components;

import java.util.Stack;
import javax.swing.*;

public class UndoManager {
    private final Stack<String> undoStack;
    private final Stack<String> redoStack;
    private final JTextArea textArea;
    private boolean isUndoingOrRedoing;

    public UndoManager(JTextArea textArea) {
        this.textArea = textArea;
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        isUndoingOrRedoing = false;

        undoStack.push("");
    }

    public synchronized void undo() {
        if (!undoStack.isEmpty()) {
            isUndoingOrRedoing = true;
            redoStack.push(textArea.getText());
            textArea.setText(undoStack.pop());
            textArea.repaint();
            isUndoingOrRedoing = false;
            printStacks("Undo performed.");
        }
    }

    public synchronized void redo() {
        if (!redoStack.isEmpty()) {
            isUndoingOrRedoing = true;
            undoStack.push(textArea.getText());
            textArea.setText(redoStack.pop());
            textArea.repaint();
            isUndoingOrRedoing = false;
            printStacks("Redo performed.");
        }
    }

    public synchronized void addUndo(String text) {
        if (!isUndoingOrRedoing) {
            undoStack.push(text);
            printStacks("Undo added.");
        }
    }

    public Stack<String> getUndoStack() {
        return undoStack;
    }

    public Stack<String> getRedoStack() {
        return redoStack;
    }

    private void printStacks(String action) {
        System.out.println(action + " Undo stack: " + undoStack);
        System.out.println("Redo stack: " + redoStack);
    }
}
