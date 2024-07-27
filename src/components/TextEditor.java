package components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;


public class TextEditor {
    private JFrame frame;
    private JTextArea textArea;
    private UndoManager undoManager;
    private ResizingComponentAdapter resizingComponentAdapter;
    private String currentText;
    private String previousText;
    private JToolBar toolBar;
    private MenuBar menuBar; // New instance variable

    public TextEditor() {
        frame = new JFrame();
        textArea = new JTextArea();
        toolBar = new JToolBar();
        menuBar = new MenuBar(); // Initialize the MenuBar
        undoManager = new UndoManager(textArea);
        resizingComponentAdapter = new ResizingComponentAdapter(frame, textArea, toolBar);
        currentText = textArea.getText();
        previousText = currentText;
        setupGUI();
        setupEvents();
        setupMenuEvents();
    }

    private void setupGUI() {
        frame.setSize(1200, 750);
        frame.add(textArea);
        frame.add(toolBar, BorderLayout.NORTH);
        frame.addComponentListener(resizingComponentAdapter);
        frame.setLayout(new BorderLayout());
        frame.setJMenuBar(menuBar.getMenuBar()); // Use the MenuBar class to get the JMenuBar
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
            }
        });

        KeyStrokeActionManager keyStrokeActionManager = new KeyStrokeActionManager(textArea, undoManager, previousText);
        keyStrokeActionManager.setupKeyStrokeActions();
    }

    private void setupMenuEvents() {

        menuBar.getMenuBar().getMenu(0).getItem(0).addActionListener(e -> {
            // Open
            System.out.println("Open");
        });
        menuBar.getMenuBar().getMenu(0).getItem(1).addActionListener(e -> {
            // Save
            System.out.println("Save");
        });

        menuBar.getMenuBar().getMenu(1).getItem(0).addActionListener(e -> {
            previousText =textArea.getText();
            undoManager.addUndo(previousText);
            textArea.cut();
        });

        menuBar.getMenuBar().getMenu(1).getItem(1).addActionListener(e -> {
            textArea.copy();
        });

        menuBar.getMenuBar().getMenu(1).getItem(2).addActionListener(e -> {
            previousText =textArea.getText();
            undoManager.addUndo(previousText);
            textArea.paste();

        });

        menuBar.getMenuBar().getMenu(1).getItem(3).addActionListener(e -> {
            undoManager.undo();
        });


    }
}