import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class Notepad {

    JFrame frame; // main frame
    JTextArea area; // to write text
    FileOperations example; // file operations

    Notepad() {
        frame = new JFrame("Notepad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        area = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(area); // will adjust the text area

        JLabel statusBar = new JLabel("Status Bar"); // status bar label

        MenuBar mb = new MenuBar(); // associating the MenuBar class
        example = new FileOperations(); // associating File Operations class

        frame.setLayout(new BorderLayout()); // setting the frame to border layout
        frame.setJMenuBar(mb.menuBar); // setting the menu Bar to appear on top of the frame
        frame.add(scrollPane, BorderLayout.CENTER); // setting the scrollPane in the center
        frame.add(statusBar, BorderLayout.SOUTH); // setting the status bar at the bottom
        frame.setSize(800, 600); // size of the frame
        frame.setVisible(true);

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

        mb.copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                area.copy();
            }
        });

        mb.cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                area.cut();
            }
        });

        mb.paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                area.paste();
            }
        });

        mb.selectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                area.selectAll();
            }
        });
    }

    private void displayFileContent(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            area.setText("");
            String line;
            while ((line = reader.readLine()) != null) {
                area.append(line + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "File Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFileContent(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            area.write(writer);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "File Save Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
