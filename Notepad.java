import javax.swing.*;
import java.awt.*;

class Notepad {

    JFrame frame;
    JTextArea area;

    Notepad() {
        frame = new JFrame("Notepad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        area = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(area);

        JLabel statusBar = new JLabel("Status Bar");

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(statusBar, BorderLayout.SOUTH);
        frame.setSize(800,600);
        frame.setVisible(true);
    }
}
