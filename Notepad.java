import java.awt.*;
import javax.swing.*;
import java.util.stream.IntStream;

public class FontChooser extends JPanel {
    private Font selectedFont;
    private JList<String> fontList;
    private JList<String> styleList;
    private JList<String> sizeList;
    private JDialog dialog;
    private boolean ok;

    public FontChooser(Font initialFont) {
        this.selectedFont = initialFont;
        initializeComponents(initialFont);
        layoutComponents();
    }

    private void initializeComponents(Font initialFont) {
        // Initialize font list with available font family names
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontList = new JList<>(fontNames);
        fontList.setSelectedValue(initialFont.getFamily(), true);

        // Initialize style list with font styles
        String[] fontStyles = {"Plain", "Italic", "Bold", "Bold Italic"};
        styleList = new JList<>(fontStyles);
        styleList.setSelectedIndex(initialFont.getStyle());

        // Initialize size list with font sizes (10 to 68 in steps of 2)
        String[] fontSizes = IntStream.range(0, 30)
                .mapToObj(i -> String.valueOf(10 + i * 2))
                .toArray(String[]::new);
        sizeList = new JList<>(fontSizes);
        sizeList.setSelectedValue(String.valueOf(initialFont.getSize()), true);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        // Create panel for font, style, and size lists
        JPanel listsPanel = new JPanel(new GridLayout(1, 3));
        listsPanel.add(new JScrollPane(fontList));
        listsPanel.add(new JScrollPane(styleList));
        listsPanel.add(new JScrollPane(sizeList));

        // Create buttons for OK and Cancel actions
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            ok = true;
            selectedFont = createFont();
            dialog.setVisible(false);
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.setVisible(false));

        // Create panel for buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        // Add components to the main panel
        add(listsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public Font createFont() {
        // Retrieve selected font properties and create a new Font object
        String fontName = fontList.getSelectedValue();
        int fontStyle = styleList.getSelectedIndex();
        int fontSize = Integer.parseInt(sizeList.getSelectedValue());
        return new Font(fontName, fontStyle, fontSize);
    }

    public boolean showDialog(Component parent, String title) {
        ok = false;

        // Create and configure the dialog
        if (dialog == null || dialog.getOwner() != SwingUtilities.getWindowAncestor(parent)) {
            dialog = new JDialog(SwingUtilities.getWindowAncestor(parent), title, Dialog.ModalityType.APPLICATION_MODAL);
            dialog.getContentPane().add(this);
            dialog.pack();
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(parent);
        }

        dialog.setVisible(true);
        return ok;
    }
}
