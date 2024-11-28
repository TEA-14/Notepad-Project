import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar  {

    JMenuBar menuBar;
    JMenu menuFile, editFile, format, about;
    JMenuItem newFile,openFile,saveFile, closeTab, newWindow, saveAll;
    JMenuItem copy,cut,paste,selectAll;
    JMenuItem font, color;
    JMenuItem aboutus;

    MenuBar() {
        menuBar = new JMenuBar(); //creating instance
        menuFile = new JMenu("File"); // creating instance of the JMenu with name
        editFile = new JMenu("Edit");
        format = new JMenu("Format");
        about = new JMenu("About");

        newFile = new JMenuItem("New file"); // creating instance
        openFile = new JMenuItem("Open");
        saveFile = new JMenuItem("Save");
        newWindow = new JMenuItem("New window");
        saveAll = new JMenuItem("Save all");
        closeTab = new JMenuItem("Close Tab");
        copy = new JMenuItem("copy");
        cut = new JMenuItem("cut");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("Select all");
        font = new JMenuItem("font");
        color = new JMenuItem("color");
        aboutus = new JMenuItem("About us");


        //cut.addActionListener(new MyAction());
        //copy.addActionListener(this);
        //paste.addActionListener(this);
        //selectAll.addActionListener(this);


        menuFile.add(newFile); // adding Menu Item to the JMenu
        menuFile.add(newWindow);
        menuFile.add(openFile);
        menuFile.add(saveFile);
        menuFile.add(saveAll);
        menuFile.add(closeTab);

        editFile.add(copy);
        editFile.add(cut);
        editFile.add(paste);
        editFile.add(selectAll);

        format.add(color);
        format.add(font);

        about.add(aboutus);

        menuBar.add(menuFile); // adding JMenu to the Menu Bar
        menuBar.add(editFile);
        menuBar.add(format);
        menuBar.add(about);





    }



    }

