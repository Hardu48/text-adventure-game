

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class GameGUI {
    private JFrame frame;
    private JTextArea storyTextArea;
    private JButton saveButton, loadButton, choiceButton, appendStoryButton, clearButton, addChoiceButton;
    private JTextField storyInputField, filenameInputField, choiceInputField, outcomeInputField;
    private GameState gameState;

    public GameGUI() {
        gameState = new GameState("Starting Location", "Welcome to your adventure!");
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Text Adventure Game");
        frame.setLayout(new BorderLayout());

        // Set up the story area with scroll pane
        storyTextArea = new JTextArea(gameState.getStory(), 15, 40);
        storyTextArea.setEditable(false);
        storyTextArea.setLineWrap(true);
        storyTextArea.setWrapStyleWord(true);
        JScrollPane storyScrollPane = new JScrollPane(storyTextArea);
        storyScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Set up the input fields for appending story, saving, loading, and adding choices
        storyInputField = new JTextField(30);
        filenameInputField = new JTextField(15);
        choiceInputField = new JTextField(10);
        outcomeInputField = new JTextField(40);

        // Buttons for various actions
        saveButton = new JButton("Save Game");
        loadButton = new JButton("Load Game");
        choiceButton = new JButton("Make a Choice");
        appendStoryButton = new JButton("Append to Story");
        clearButton = new JButton("Clear Game");
        addChoiceButton = new JButton("Add Custom Choice");

        // Panel for input and action buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adding components to the input panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Append to Story: "), gbc);
        gbc.gridx = 1;
        inputPanel.add(storyInputField, gbc);
        gbc.gridx = 2;
        inputPanel.add(appendStoryButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Filename: "), gbc);
        gbc.gridx = 1;
        inputPanel.add(filenameInputField, gbc);
        gbc.gridx = 2;
        inputPanel.add(saveButton, gbc);
        gbc.gridx = 3;
        inputPanel.add(loadButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Custom Choice: "), gbc);
        gbc.gridx = 1;
        inputPanel.add(choiceInputField, gbc);
        gbc.gridx = 2;
        inputPanel.add(new JLabel("Outcome: "), gbc);
        gbc.gridx = 3;
        inputPanel.add(outcomeInputField, gbc);
        gbc.gridx = 4;
        inputPanel.add(addChoiceButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(choiceButton, gbc);
        gbc.gridx = 1;
        inputPanel.add(clearButton, gbc);

        // Adding scrollable text area to the frame
        frame.add(storyScrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Adding action listeners to buttons
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = filenameInputField.getText();
                if (!filename.isEmpty()) {
                    GameSaveLoad.saveGame(gameState, filename + ".ser");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a filename to save.");
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = filenameInputField.getText();
                if (!filename.isEmpty()) {
                    GameState loadedState = GameSaveLoad.loadGame(filename + ".ser");
                    if (loadedState != null) {
                        gameState = loadedState;
                        updateText();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to load game. Check the filename.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a filename to load.");
                }
            }
        });

        choiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeChoice();
            }
        });

        appendStoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String additionalText = storyInputField.getText();
                if (!additionalText.isEmpty()) {
                    gameState.appendToStory(additionalText);
                    updateText();
                    storyInputField.setText("");  // Clear input field
                }
            }
        });

        addChoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choice = choiceInputField.getText();
                String outcome = outcomeInputField.getText();
                if (!choice.isEmpty() && !outcome.isEmpty()) {
                    gameState.addCustomChoice(choice, outcome);
                    choiceInputField.setText("");
                    outcomeInputField.setText("");
                    JOptionPane.showMessageDialog(frame, "Custom choice added!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter both a choice and its outcome.");
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState.clear();  // Clear the game state
                updateText();  // Update the text area to reflect changes
            }
        });

        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void makeChoice() {
        HashMap<String, String> customChoices = gameState.getCustomChoices();
        if (customChoices.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No choices available! Please add some choices first.");
            return;
        }

        String[] options = customChoices.keySet().toArray(new String[0]);

        // Present the player with a dialog to make a choice
        String choice = (String) JOptionPane.showInputDialog(
            frame,
            "What would you like to do?",
            "Choose an action",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0] // Default selection
        );

        if (choice != null && customChoices.containsKey(choice)) {
            String outcome = customChoices.get(choice);
            gameState.appendToStory(outcome);  // Append the outcome associated with the chosen option
            updateText();  // Update the story text area
        }
    }

    private void updateText() {
        storyTextArea.setText(gameState.getStory());
    }

    public static void main(String[] args) {
        new GameGUI();
    }
}
