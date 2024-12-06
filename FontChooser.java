import java.awt.*;
import javax.swing.*;

public class FontChooser extends JPanel {
    private Font selectedFont;
    private JList<String> fontList;
    private JList<String> styleList;
    private JList<String> sizeList;
    private JDialog dialog;
    private boolean ok;

    public FontChooser(Font initialFont) {
        selectedFont = initialFont;
        initializeComponents(initialFont);
        layoutComponents();
    }

    private void initializeComponents(Font initialFont) {
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontList = new JList<>(fontNames);
        fontList.setSelectedValue(initialFont.getFamily(), true);

        String[] fontStyles = {"Plain", "Italic", "Bold", "Bold Italic"};
        styleList = new JList<>(fontStyles);
        styleList.setSelectedIndex(initialFont.getStyle());

        String[] fontSizes = new String[30];
        for (int i = 0; i < 30; i++) {
            fontSizes[i] = String.valueOf(10 + i * 2);
        }
        sizeList = new JList<>(fontSizes);
        sizeList.setSelectedValue(String.valueOf(initialFont.getSize()), true);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        JPanel listsPanel = new JPanel(new GridLayout(1, 3));
        listsPanel.add(new JScrollPane(fontList));
        listsPanel.add(new JScrollPane(styleList));
        listsPanel.add(new JScrollPane(sizeList));

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            ok = true;
            selectedFont = createFont();
            dialog.setVisible(false);
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.setVisible(false));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        add(listsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public Font createFont() {
        String fontName = fontList.getSelectedValue();
        int fontStyle = styleList.getSelectedIndex();
        int fontSize = Integer.parseInt(sizeList.getSelectedValue());
        return new Font(fontName, fontStyle, fontSize);
    }

    public boolean showDialog(Component parent, String title) {
        ok = false;

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
