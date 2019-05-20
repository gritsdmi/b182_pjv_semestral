package start.Logic;

import start.GamePanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SavedGamesController extends JDialog {

    private SaveLoadController slc;
    private final String[] Item = new String[1];//selected filename
    private boolean levelSelect = false;

    public SavedGamesController(GamePanel gp, SaveLoadController slc) {
        super(gp.getMainFrame(), "Modal", true);
        this.slc = slc;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        displayContent();

    }

    public boolean isLevelSelect() {
        return levelSelect;
    }

    private void displayContent() {

        JPanel jP1 = new JPanel();
        jP1.setLayout(null);
        final JLabel label = new JLabel("Choose saved game: ");
        final JLabel errorLabel = new JLabel("There are no saved games yet.\nPlease go back and start new game.");

        JButton goButton = new JButton("Go!");
        goButton.setEnabled(false);
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                levelSelect = true;
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            setVisible(false);
            dispose();
        });

        JList<String> jList = new JList<>(slc.getExistingSavedGames());
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.setPrototypeCellValue("1111111111111111111111111111111111111111111111111111");
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selected = ((JList<?>) e.getSource()).getSelectedIndex();
                goButton.setEnabled(true);
                Item[0] = slc.getExistingSavedGames()[selected];
            }
        });


        if (slc.getExistingSavedGames().length == 0) {
            System.out.println(slc.getExistingSavedGames().length);
            jP1.add(errorLabel);
        }

        jP1.add(label);
        Insets insets = jP1.getInsets();
        Dimension size = label.getPreferredSize();
        label.setBounds(insets.left + 50, insets.top + 50, size.width, size.height);

        jP1.add(jList);
        jP1.add(goButton);
        jP1.add(backButton);

        size = errorLabel.getPreferredSize();
        errorLabel.setBounds(insets.left + 50, insets.top + 70, size.width, size.height);

        size = jList.getPreferredSize();
        jList.setBounds(insets.left + 50, insets.top + 100, size.width, size.height);

        size = goButton.getPreferredSize();
        goButton.setBounds(insets.left + 300, insets.top + 200, size.width, size.height);

        size = backButton.getPreferredSize();
        backButton.setBounds(insets.left + 100, insets.top + 200, size.width, size.height);


        setContentPane(jP1);
        setSize(new Dimension(600, 300));
        setLocationRelativeTo(null);
        setResizable(false);
        requestFocus();
        setVisible(true);

    }

    public String getSelectedSave() {
        return Item[0];
    }
}
