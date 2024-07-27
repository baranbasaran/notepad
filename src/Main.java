import components.TextEditor;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TextEditor::new);
    }
}