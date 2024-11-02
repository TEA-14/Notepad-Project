import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar  {

    JMenuBar menuBar;
    JMenu menuFile, editFile;
    JMenuItem newFile,openFile,saveFile;
    JMenuItem copy,cut,paste,selectAll;

    MenuBar() {
        menuBar = new JMenuBar(); //creating instance
        menuFile = new JMenu("File"); // creating instance of the JMenu with name
        editFile = new JMenu("Edit");

        newFile = new JMenuItem("New"); // creating instance
        openFile = new JMenuItem("Open");
        saveFile = new JMenuItem("Save");
        copy = new JMenuItem("copy");
        cut = new JMenuItem("cut");
        paste = new JMenuItem("paste");
        selectAll = new JMenuItem("Select all");

        //cut.addActionListener(new MyAction());
        //copy.addActionListener(this);
        //paste.addActionListener(this);
        //selectAll.addActionListener(this);


        menuFile.add(newFile); // adding Menu Item to the JMenu
        menuFile.add(openFile);
        menuFile.add(saveFile);

        editFile.add(copy);
        editFile.add(cut);
        editFile.add(paste);
        editFile.add(selectAll);

        menuBar.add(menuFile); // adding JMenu to the Menu Bar
        menuBar.add(editFile);





    }



    }

