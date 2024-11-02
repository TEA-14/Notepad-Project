import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Notepad {

    JFrame frame; //main frame
    JTextArea area; //to write text

    Notepad() {
        frame = new JFrame("Notepad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        area = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(area); //will adjust the text area

        JLabel statusBar = new JLabel("Status Bar"); //status bar label

        MenuBar mb = new MenuBar(); //associating the MenuBar class

        frame.setLayout(new BorderLayout()); //setting the frame to border layout
        frame.setJMenuBar(mb.menuBar); // setting the menu Bar to appear on top of the frame
        frame.add(scrollPane, BorderLayout.CENTER); // setting the scrollPane in the center
        frame.add(statusBar, BorderLayout.SOUTH); // setting the status bar at the bottom
        frame.setSize(800,600); // size of the frame
        frame.setVisible(true);

        mb.copy.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                area.copy();
            }
        });

        mb.cut.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                area.cut();
            }
        });

        mb.paste.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                area.paste();
            }
        });

        mb.selectAll.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                area.selectAll();
            }
        });
    }
}
