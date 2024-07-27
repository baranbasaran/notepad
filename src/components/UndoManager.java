package components;
import java.util.Stack;
import javax.swing.*;

public class UndoManager {
    private Stack<String> undoStack;
    private Stack<String> redoStack;
    private JTextArea textArea;
    private boolean isUndoingOrRedoing;
    public UndoManager(JTextArea textArea) {
        this.textArea = textArea;
        undoStack = new Stack<>() ;
        redoStack = new Stack<>();
        isUndoingOrRedoing = false;

        undoStack.push("");

    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            isUndoingOrRedoing = true;
            String text = undoStack.pop();
            redoStack.push(textArea.getText());
            textArea.setText(text);
            textArea.repaint();  // Add this line

            isUndoingOrRedoing = false;
            System.out.println("Undo performed. Undo stack: " + undoStack);
            System.out.println("Redo stack: " + redoStack);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            isUndoingOrRedoing = true;
            String text = redoStack.pop();
            undoStack.push(textArea.getText());
            textArea.setText(text);
            textArea.repaint();  // Add this line

            isUndoingOrRedoing = false;
            System.out.println("Redo performed. Undo stack: " + undoStack);
            System.out.println("Redo stack: " + redoStack);
        }
    }

    public void addUndo(String text){
        if (!isUndoingOrRedoing) {
            undoStack.push(text);
            System.out.println("Undo added. Undo stack: " + undoStack);
        }
    }

    public Stack<String> getUndoStack() {
        return undoStack;
    }

    public Stack<String> getRedoStack() {
        return redoStack;
    }
}