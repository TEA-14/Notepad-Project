import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class Notepad {

    JFrame frame; // main frame
    JTabbedPane tabbedPane; // tabbed pane to manage multiple text areas
    FileOperations example; // file operations

    Notepad() {
        frame = new JFrame("Notepad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        addNewTab(); // Add an initial tab

        JLabel statusBar = new JLabel("Status Bar"); // status bar label

        MenuBar mb = new MenuBar(); // associating the MenuBar class
        example = new FileOperations(); // associating File Operations class

        frame.setLayout(new BorderLayout()); // setting the frame to border layout
        frame.setJMenuBar(mb.menuBar); // setting the menu Bar to appear on top of the frame
        frame.add(tabbedPane, BorderLayout.CENTER); // setting the tabbed pane in the center
        frame.add(statusBar, BorderLayout.SOUTH); // setting the status bar at the bottom
        frame.setSize(800, 600); // size of the frame
        frame.setVisible(true);

        // New File action
        mb.newFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewTab();
            }
        });

        // Open File action
        mb.openFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = example.fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = example.fileChooser.getSelectedFile();
                    displayFileContent(selectedFile);
                }
            }
        });

        // Save File action
        mb.saveFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = example.fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = example.fileChooser.getSelectedFile();
                    saveFileContent(selectedFile);
                }
            }
        });

        // Copy action
        mb.copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getCurrentTextArea().copy();
            }
        });

        // Cut action
        mb.cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getCurrentTextArea().cut();
            }
        });

        // Paste action
        mb.paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getCurrentTextArea().paste();
            }
        });

        // Select All action
        mb.selectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getCurrentTextArea().selectAll();
            }
        });
    }

    private void addNewTab() {
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        tabbedPane.addTab("Untitled", scrollPane);
        tabbedPane.setSelectedComponent(scrollPane);
    }

    private JTextArea getCurrentTextArea() {
        JScrollPane scrollPane = (JScrollPane) tabbedPane.getSelectedComponent();
        return (JTextArea) scrollPane.getViewport().getView();
    }

    private void displayFileContent(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            JTextArea currentTextArea = getCurrentTextArea();
            currentTextArea.setText("");
            String line;
            while ((line = reader.readLine()) != null) {
                currentTextArea.append(line + "\n");
            }
            tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), file.getName());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "File Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFileContent(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            JTextArea currentTextArea = getCurrentTextArea();
            currentTextArea.write(writer);
            tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), file.getName());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "File Save Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
