import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

class Notepad {

    JFrame frame; // main frame
    JTabbedPane tabbedPane; // tabbed pane to manage multiple text areas
    FileOperations example; // file operations
    JLabel statusBar; // status bar label

    Notepad() {
        frame = new JFrame("Notepad");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        addNewTab(); // Add an initial tab

        statusBar = new JLabel("Line: 1, Column: 1"); // status bar label

        MenuBar mb = new MenuBar(); // associating the MenuBar class
        example = new FileOperations(); // associating File Operations class

        frame.setLayout(new BorderLayout()); // setting the frame to border layout
        frame.setJMenuBar(mb.menuBar); // setting the menu Bar to appear on top of the frame
        frame.add(tabbedPane, BorderLayout.CENTER); // setting the tabbed pane in the center
        frame.add(statusBar, BorderLayout.SOUTH); // setting the status bar at the bottom
        frame.setSize(800, 600); // size of the frame
        frame.setVisible(true);

        tabbedPane.addChangeListener(e -> {
            JTextArea currentTextArea = getCurrentTextArea();
            if (currentTextArea != null) {
                updateStatusBar(currentTextArea);
            }
        });

        // New File action
        mb.newFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewTab();
            }
        });

        // New Window action
        mb.newWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Notepad();
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

        // Save All action
        mb.saveAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAllTabs();
            }
        });

        // Close Tab action
        mb.closeTab.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = tabbedPane.getSelectedIndex();
                if (index != -1) {
                    tabbedPane.removeTabAt(index);
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

        // Change Font action
        mb.font.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeFont();
            }
        });

        // Change Color action
        mb.color.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showColorChooser();
            }
        });

        // About Us action
        mb.aboutus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAboutDialog();
            }
        });
    }

    private void addNewTab() {
        JTextArea textArea = new JTextArea();
        textArea.setTransferHandler(new FileDropHandler()); // Set the transfer handler for drag-and-drop
        JScrollPane scrollPane = new JScrollPane(textArea);
        tabbedPane.addTab("Untitled", scrollPane);
        tabbedPane.setSelectedComponent(scrollPane);

        textArea.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                updateStatusBar(textArea);
            }
        });
    }


    private JTextArea getCurrentTextArea() {
        JScrollPane scrollPane = (JScrollPane) tabbedPane.getSelectedComponent();
        return (JTextArea) scrollPane.getViewport().getView();
    }

    public void displayFileContent(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            JTextArea currentTextArea = getCurrentTextArea();
            currentTextArea.setText("");
            String line;
            while ((line = reader.readLine()) != null) {
                currentTextArea.append(line + "\n");
            }
            tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), file.getName());
            updateStatusBar(currentTextArea); // Update status bar
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "File Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFileContent(File file) {
        if (file.exists()) {
            int option = JOptionPane.showConfirmDialog(
                    frame,
                    "File already exists. Do you want to overwrite it?",
                    "File Exists",
                    JOptionPane.YES_NO_OPTION
            );
            if (option != JOptionPane.YES_OPTION) {
                return;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            JTextArea currentTextArea = getCurrentTextArea();
            currentTextArea.write(writer);
            tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), file.getName());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "File Save Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveAllTabs() {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setSelectedIndex(i);
            int result = example.fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = example.fileChooser.getSelectedFile();
                if (selectedFile.exists()) {
                    int option = JOptionPane.showConfirmDialog(
                            frame,
                            "File " + selectedFile.getName() + " already exists. Do you want to overwrite it?",
                            "File Exists",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (option != JOptionPane.YES_OPTION) {
                        continue;
                    }
                }
                saveFileContent(selectedFile);
            }
        }
    }

    private void changeFont() {
        JTextArea currentTextArea = getCurrentTextArea();
        Font currentFont = currentTextArea.getFont();
        FontChooser fontChooser = new FontChooser(currentFont);
        if (fontChooser.showDialog(frame, "Choose a font")) {
            currentTextArea.setFont(fontChooser.createFont());
        }
    }

    private void showColorChooser() {
        JTextArea currentTextArea = getCurrentTextArea();
        Color initialColor = currentTextArea.getForeground();
        Color selectedColor = JColorChooser.showDialog(frame, "Choose Text Color", initialColor);
        if (selectedColor != null) {
            currentTextArea.setForeground(selectedColor);
        }
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(frame,
                "Notepad developed by Tayyab Ejaz Ahmed",
                "About Us",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateStatusBar(JTextArea textArea) {
        int caretPosition = textArea.getCaretPosition();
        int lineNumber = 0;
        int columnNumber = 0;
        try {
            lineNumber = textArea.getLineOfOffset(caretPosition) + 1;
            columnNumber = caretPosition - textArea.getLineStartOffset(lineNumber - 1) + 1;
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        statusBar.setText("Line: " + lineNumber + ", Column: " + columnNumber);
    }


    class FileDropHandler extends TransferHandler {
        @Override
        public boolean canImport(TransferSupport support) {
            // We only support file drops
            if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                return false;
            }
            return true;
        }

        @Override
        public boolean importData(TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }

            // Get the file list that is being dropped
            Transferable t = support.getTransferable();
            try {
                java.util.List<File> files = (java.util.List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
                for (File file : files) {
                    displayFileContent(file);
                }
            } catch (UnsupportedFlavorException | IOException e) {
                return false;
            }
            return true;
        }
    }



}
