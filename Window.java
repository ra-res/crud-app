import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

class Window extends JFrame {
    private ArrayList<ID> ids = new ArrayList<>();
    private JPanel northPanel, centrePanel, southPanel;
    private JTextField textFieldNorthPanel, redValueInputTextField, greenValueInputTextField, blueValueInputTextField;
    private int textField;
    private String ErrorInputMessage, successfulInputMessage;

    /**
     * Method: Window() Description: Constructor used to initilise the JFrame window
     * with specific settings. Calls drawInputNorthPanel(), drawCenterPanel(),
     * drawButtonsSouthPanel() to draw different elements of the GUI
     */
    public Window() {
        super("ID List");
        setLayout(new BorderLayout());
        add(northPanel = drawInputNorthPanel(), "North");
        add(centrePanel = drawCenterPanel(), "Center");
        add(southPanel = drawButtonsSouthPanel(), "South");
        setMinimumSize(new Dimension(400, 400));
        pack();
        setSize(1000, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Method: drawCenterPanelWithUserMessage() Description: Method is being called
     * once a user enters an ID an presses "Insert ID to list" Button; Adds a JLabel
     * with relevant error message to the JPanel. Return: JPanel containing a JLabel
     * with the relevant message in response to user input.
     */
    public JPanel drawCenterPanelWithUserMessage() {
        JPanel IDListPanelCenter = new JPanel();

        IDListPanelCenter.setBackground(new Color(16, 26, 42));
        IDListPanelCenter.setPreferredSize(new Dimension(400, 300));
        IDListPanelCenter.setLayout(new FlowLayout(FlowLayout.CENTER));
        if (textField != -1) {
            styleIDLabel(IDListPanelCenter, new JLabel(successfulInputMessage), Color.BLACK);
        } else {
            styleIDLabel(IDListPanelCenter, new JLabel(ErrorInputMessage), Color.BLACK);
        }

        return IDListPanelCenter;
    }

    /**
     * Method: drawCenterPanel() Description: Methods draws the center part of the
     * Border Layout when 'Display all IDs' button is clicked Return: JPanel
     * containing list of all IDS inside the array list.
     */
    public JPanel drawCenterPanel() {
        JPanel IDListPanelCenter = new JPanel();

        IDListPanelCenter.setBackground(new Color(16, 26, 42));
        IDListPanelCenter.setPreferredSize(new Dimension(400, 300));
        IDListPanelCenter.setLayout(new FlowLayout(FlowLayout.CENTER));

        if (ids.size() == 0) {
            JLabel emptyListErrorMessage = new JLabel("No ID's to display");
            styleIDLabel(IDListPanelCenter, emptyListErrorMessage, Color.BLACK);
        } else {
            for (ID id : ids) {
                styleIDLabel(IDListPanelCenter, new JLabel(id.toString()), fetchColor());
            }
        }
        return IDListPanelCenter;
    }

    /**
     * Method: DrawInputNorthPanel() Description: Method draws the North part of the
     * Border Layout when constructor is called Return: JPanel containing all input
     * fields and labels
     */
    public JPanel drawInputNorthPanel() {
        JPanel inputPanelNorth = new JPanel();
        inputPanelNorth.setBackground(new Color(150, 160, 200));
        inputPanelNorth.setPreferredSize(new Dimension(100, 100));
        inputPanelNorth.setLayout(new GridBagLayout());

        JLabel inputLabelNorthPanel = new JLabel("Please enter input here  ");
        inputLabelNorthPanel.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        textFieldNorthPanel = new JTextField(6);

        JLabel redValueInputLabel = new JLabel("  Red  ");
        redValueInputLabel.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        redValueInputTextField = new JTextField("0", 2);

        JLabel greenValueInputLabel = new JLabel("  Green  ");
        greenValueInputLabel.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        greenValueInputTextField = new JTextField("0", 2);

        JLabel blueValueInputLabel = new JLabel("  Blue  ");
        blueValueInputLabel.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        blueValueInputTextField = new JTextField("0", 2);

        inputPanelNorth.add(inputLabelNorthPanel);
        inputPanelNorth.add(textFieldNorthPanel);
        inputPanelNorth.add(redValueInputLabel);
        inputPanelNorth.add(redValueInputTextField);
        inputPanelNorth.add(greenValueInputLabel);
        inputPanelNorth.add(greenValueInputTextField);
        inputPanelNorth.add(blueValueInputLabel);
        inputPanelNorth.add(blueValueInputTextField);

        return inputPanelNorth;
    }

    /**
     * Method: drawButtonsSouthPanel() Description: Method draws the South part of
     * the Border Layout when constructor is called; Creates an array of Buttons by
     * calling setButtons() method; For loops through each button inside the list,
     * styles it and adds it to JPanel Return: JPanel containing all buttons
     */
    public JPanel drawButtonsSouthPanel() {
        JPanel buttonsPanelSouth = new JPanel();
        buttonsPanelSouth.setBackground(new Color(16, 26, 42));
        buttonsPanelSouth.setPreferredSize(new Dimension(100, 100));
        buttonsPanelSouth.setLayout(new GridLayout(1, 4, 5, 5));

        ArrayList<JButton> buttons = setButtons();

        for (JButton b : buttons) {
            b.setBorder(null);
            b.setForeground(Color.BLACK);
            b.setBackground(new Color(150, 160, 200));
            buttonsPanelSouth.add(b);
        }
        return buttonsPanelSouth;
    }

    /**
     * Method: setButtons() Description: Initialises all buttons required in the
     * application; Adds Action Listener to each button and relevant implementation;
     * Adds all buttons to ArrayList Return: ArrayList containing all buttons
     */
    public ArrayList<JButton> setButtons() {
        ArrayList<JButton> temp = new ArrayList<>();

        JButton insertIDToList = new JButton("Insert ID to List");
        insertIDToList.addActionListener(e -> {
            textField = fetchAndValidateIDInput();
            if (textField != -1) {
                ids.add(new ID(textField));
            }
            repaintCentrePanelWithUserMessage();
            textFieldNorthPanel.setText("");
        });

        JButton displayList = new JButton("Display List Contents");
        displayList.addActionListener(e -> repaintCentrePanelWithIDList());

        JButton deleteIdFromList = new JButton("Delete ID from List");
        deleteIdFromList.addActionListener(e -> {
            textField = fetchAndValidateIDInput();
            if (textField != -1) {
                ids.removeIf(id -> id.getID() == textField);
                repaintCentrePanelWithIDList();
            }
            textFieldNorthPanel.setText("");
        });

        JButton sortList = new JButton("Sort List");
        sortList.addActionListener(e -> {
            Collections.sort(ids);
            repaintCentrePanelWithIDList();
        });

        JButton clearList = new JButton("ClearList");
        clearList.addActionListener(e -> {
            ids.clear();
            repaintCentrePanelWithIDList();
        });

        temp.add(insertIDToList);
        temp.add(displayList);
        temp.add(deleteIdFromList);
        temp.add(sortList);
        temp.add(clearList);

        return temp;
    }

    /**
     * Method: styleIDLabel() Parameters: JPanel IDListPanelCenter; JPanel to add
     * the label to JLabel tempLabelToStyle; Label to be styled Color C; Desired
     * foreground color of the JLabel Description: Applies styles to a JLabel, then
     * adds JLabel to JPanel passed as argument; Return: void
     */
    private void styleIDLabel(JPanel IDListPanelCenter, JLabel tempLabelToStyle, Color C) {
        tempLabelToStyle.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        tempLabelToStyle.setHorizontalAlignment(SwingConstants.CENTER);
        tempLabelToStyle.setBorder(new EmptyBorder(10, 10, 10, 10));
        tempLabelToStyle.setOpaque(true);
        tempLabelToStyle.setBackground(new Color(150, 160, 200));
        tempLabelToStyle.setForeground(C);
        IDListPanelCenter.add(tempLabelToStyle);
    }

    /**
     * Method: fetchAndValidateIDInput() Description: Stores user input from text
     * field inside a string variable; Checks to see if input is empty; Checks to
     * see if input is a valid integer; Checks to see if input is within
     * 100000-999999 range; Modifies ErrorInputMessage and sucessfulInputMessage
     * variables accordingly. Return: int, returns user input if it is valid, else
     * returns -1;
     */
    private int fetchAndValidateIDInput() {
        String tempTextField = textFieldNorthPanel.getText();
        try {
            if (tempTextField.equals("")) {
                ErrorInputMessage = "Input Field is empty. Please enter a 6 digit number!";
                return -1;
            }
            int input = Integer.parseInt(textFieldNorthPanel.getText());
            if (input >= 100000 && input <= 999999) {
                successfulInputMessage = "The ID '" + tempTextField + "' has been added to the list";
                return input;
            }
            throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            ErrorInputMessage = "The ID '" + tempTextField
                    + "' was not added to the list as it is not a valid ID. Please enter a 6 digit number!";
            return -1;
        }
    }

    /**
     * Method: fetchColor() Description: Stores RBG values from input field inside
     * int r, g, b variables; Checks to see if input is a valid integer; Checks to
     * see if input is within 0-255 range; Return: returns color according to the
     * r,g,b values passed by user. If input is invalid returns Color.BLACK
     */
    private Color fetchColor() {
        try {
            int r = Integer.parseInt(redValueInputTextField.getText());
            int g = Integer.parseInt(greenValueInputTextField.getText());
            int b = Integer.parseInt(blueValueInputTextField.getText());
            if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
                throw new IllegalArgumentException();
            }
            return new Color(r, g, b);
        } catch (IllegalArgumentException e) {
            redValueInputTextField.setText("0");
            greenValueInputTextField.setText("0");
            blueValueInputTextField.setText("0");
            JOptionPane.showMessageDialog(null, "Invalid RGB Values");
            return Color.BLACK;
        }
    }

    /**
     * Method: repaintCentrePanelWithUserMessage() Description: updates Center Panel
     * with relevant user message; Removes the panel, re-creates it and then adds it
     * to JFrame again; Calls revalidate() and repaint() to update panel in real
     * time. Return: void
     */
    private void repaintCentrePanelWithUserMessage() {
        remove(centrePanel);
        centrePanel = drawCenterPanelWithUserMessage();
        add(centrePanel);
        revalidate();
        repaint();
    }

    /**
     * Method: repaintCentrePanelWithUserMessage() Description: updates Center Panel
     * with list of IDs once 'Display all IDS' button has been pressed; Removes the
     * panel, re-creates it and then adds it to JFrame again; Calls revalidate() and
     * repaint() to update panel in real time. Return: void
     */
    private void repaintCentrePanelWithIDList() {
        remove(centrePanel);
        centrePanel = drawCenterPanel();
        add(centrePanel);
        revalidate();
        repaint();
    }
}
