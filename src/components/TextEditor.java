package components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextEditor {
    private JFrame frame;
    private JTextArea textArea;
    private UndoManager undoManager;
    private String currentText;
    private String previousText;
    private JToolBar toolBar;
    private MenuBar menuBar;
    private JScrollPane scrollPane;
    private CustomCaret customCaret;
    private JLabel statusBar; // Add status bar

    public TextEditor() {
        frame = new JFrame();
        frame.setTitle("Text Editor");
        textArea = new JTextArea();
        toolBar = new JToolBar();
        menuBar = new MenuBar();
        undoManager = new UndoManager(textArea);
        customCaret = new CustomCaret();
        currentText = textArea.getText();
        previousText = currentText;
        statusBar = new JLabel("Line: 1, Column: 1");

        setupGUI();
        setupEvents();
        setupMenuEvents();
    }

    private void setupGUI() {
        textArea.setFont(new Font("Arial", Font.BOLD, 14));
        textArea.setBackground(new Color(43, 43, 43));
        textArea.setForeground(new Color(169, 183, 198));
        textArea.setCaret(customCaret.getCaret());
        textArea.setCaretColor(Color.WHITE);

        scrollPane = new JScrollPane(textArea);
        LineNumberHeaderView lineNumberHeaderView = new LineNumberHeaderView(textArea);
        scrollPane.setRowHeaderView(lineNumberHeaderView);

        frame.setSize(1200, 750);
        frame.setLayout(new BorderLayout());
        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(statusBar, BorderLayout.SOUTH);
        frame.setJMenuBar(menuBar.getMenuBar());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void setupEvents() {
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                handleTextChange();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                handleTextChange();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                handleTextChange();
            }

            private void handleTextChange() {
                previousText = currentText;
                currentText = textArea.getText();
                undoManager.addUndo(previousText);
                updateStatusBar();
            }
        });
        textArea.addCaretListener(e -> updateStatusBar());


        KeyStrokeActionManager keyStrokeActionManager = new KeyStrokeActionManager(textArea, undoManager, previousText);
        keyStrokeActionManager.setupKeyStrokeActions();
    }

    private void setupMenuEvents() {
        menuBar.getMenuBar().getMenu(0).getItem(0).addActionListener(e -> openFile());
        menuBar.getMenuBar().getMenu(0).getItem(1).addActionListener(e -> saveFile());
        menuBar.getMenuBar().getMenu(1).getItem(0).addActionListener(e -> {
            previousText = textArea.getText();
            undoManager.addUndo(previousText);
            textArea.cut();
        });
        menuBar.getMenuBar().getMenu(1).getItem(1).addActionListener(e -> textArea.copy());
        menuBar.getMenuBar().getMenu(1).getItem(2).addActionListener(e -> {
            previousText = textArea.getText();
            undoManager.addUndo(previousText);
            textArea.paste();
        });
        menuBar.getMenuBar().getMenu(1).getItem(3).addActionListener(e -> undoManager.undo());
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                textArea.setText("");
                File file = new File(selectedFile.getAbsolutePath());
                java.util.Scanner scanner = new java.util.Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    textArea.append(line + "\n");
                }
                scanner.close();
                undoManager.getUndoStack().clear();
                undoManager.getRedoStack().clear();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(fileToSave);
                writer.write(textArea.getText());
                writer.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
    private void updateStatusBar() {
        int caretPos = textArea.getCaretPosition();
        int lineNum = 1;
        int columnNum = 1;

        try {
            lineNum = textArea.getLineOfOffset(caretPos) + 1;
            columnNum = caretPos - textArea.getLineStartOffset(lineNum - 1) + 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        statusBar.setText("Line: " + lineNum + ", Column: " + columnNum);
    }
}
