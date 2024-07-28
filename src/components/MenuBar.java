package components;

import javax.swing.*;

public class MenuBar {
    private JMenuBar menuBar;

    public MenuBar() {
        menuBar = new JMenuBar();
        setupMenuBar();
    }

    private void setupMenuBar() {
        JMenu fileMenu = createMenu("File", new String[]{"Open", "Save"});
        JMenu editMenu = createMenu("Edit", new String[]{"Cut", "Copy", "Paste", "Undo"});

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
    }

    private JMenu createMenu(String title, String[] items) {
        JMenu menu = new JMenu(title);
        for (String item : items) {
            menu.add(new JMenuItem(item));
        }
        return menu;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
