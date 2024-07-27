package components;

import javax.swing.*;

public class MenuBar {
    private JMenuBar menuBar;

    public MenuBar() {
        menuBar = new JMenuBar();
        setupMenuBar();
    }

    private void setupMenuBar() {
        // Create a menu
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");



        // FILE MENU ITEMS
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        //EDIT MENU ITEMS
        JMenuItem cutItem = new JMenuItem("Cut");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste");
        JMenuItem undoItem = new JMenuItem("Undo");



        // File menu items
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        //EDIT MENU ITEMS
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(undoItem);
        // Add menu to menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

}