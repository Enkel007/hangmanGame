import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hangman extends JFrame implements ActionListener {
    //counts the number of incorrect guesses made by the player
    private int incorrectGuesses;

    //store the challenge from the WordDB here
    private String[] wordChallenge;

    private final WordDB wordDB;

    private JLabel hangmanImage, categorylabel, hiddenWordLabel;

    private JButton[] letterButtons;

    public Hangman(){
        super("Hangman Game (Java Ed.)");
        setSize(CommonConstants.FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);

        //initiating vars
        wordDB = new WordDB();
        letterButtons = new JButton[26];
        wordChallenge = wordDB.loadChallenge();

        addGUIComponents();
    }

    private void addGUIComponents(){
        //hangman image
        hangmanImage = CustomTools.loadImage(CommonConstants.IMAGE_PATH);
        hangmanImage.setBounds(0, 0, hangmanImage.getPreferredSize().width, hangmanImage.getPreferredSize().height);

        //category display
        categorylabel = new JLabel(wordChallenge[0]);
        categorylabel.setHorizontalAlignment(SwingConstants.CENTER);
        categorylabel.setOpaque(true);
        categorylabel.setForeground(Color.WHITE);
        categorylabel.setBackground(CommonConstants.SECONDARY_COLOR);
        categorylabel.setBorder(BorderFactory.createLineBorder(CommonConstants.SECONDARY_COLOR));
        categorylabel.setBounds(
                0,
                hangmanImage.getPreferredSize().height - 20,
                hangmanImage.getPreferredSize().width,
                categorylabel.getPreferredSize().height
        );

        //hidden word
        hiddenWordLabel = new JLabel(CustomTools.hiddenWords(wordChallenge[1]));
        hiddenWordLabel.setForeground(Color.WHITE);
        hiddenWordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hiddenWordLabel.setBounds(
                0,
                categorylabel.getY() + categorylabel.getPreferredSize().height + 50,
                CommonConstants.FRAME_SIZE.width,
                hiddenWordLabel.getPreferredSize().height
        );

        //letter buttons
        GridLayout gridLayout = new GridLayout(4, 7);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(
                -5,
                hiddenWordLabel.getY() + hiddenWordLabel.getPreferredSize().height,
                CommonConstants.BUTTON_PANEL_SIZE.width,
                CommonConstants.BUTTON_PANEL_SIZE.height
        );
        buttonPanel.setLayout(gridLayout);

        for(char c = 'A'; c <= 'Z'; c++){
            JButton button = new JButton(Character.toString(c));
            button.setBackground(CommonConstants.PRIMARY_COLOR);
            button.setForeground(Color.WHITE);
            button.addActionListener(this);

            //using ASCII values to calculate current index
            int currentIndex = c - 'A';

            letterButtons[currentIndex] = button;
            buttonPanel.add(letterButtons[currentIndex]);
        }

        //reset button
        JButton resetButton = new JButton("Reset");
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(CommonConstants.SECONDARY_COLOR);
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);

        //quit button
        JButton quitButton = new JButton("Quit");
        quitButton.setForeground(Color.WHITE);
        quitButton.setBackground(CommonConstants.SECONDARY_COLOR);
        quitButton.addActionListener(this);
        buttonPanel.add(quitButton);

        getContentPane().add(hiddenWordLabel);
        getContentPane().add(categorylabel);
        getContentPane().add(hangmanImage);
        getContentPane().add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("Reset")){
            resetGame();
        }else if(command.equals("Quit")){
            dispose();
            return;
        }else{
            //letter buttons

            //disable button
            JButton button = (JButton)e.getSource();
            button.setEnabled(false);

            //check if the word contains the user's guess
            if(wordChallenge[1].contains(command)){
                //indicate that the user chose the correct letter
                button.setBackground(Color.GREEN);

                //store the hidden word in a char array, as to update the hidden text
                char[] hiddenWord = hiddenWordLabel.getText().toCharArray();

                for(int i = 0;i < wordChallenge[1].length(); i++){
                    //update _ to correct letter
                    if(wordChallenge[1].charAt(i) == command.charAt(i)){
                        hiddenWord[i] = command.charAt(0);
                    }
                }

                //update hiddenWordLabel
                hiddenWordLabel.setText(String.valueOf(hiddenWord));

                //the user guessed the word
            }else{
                //indicate that the user chose the wrong letter
                button.setBackground(Color.RED);

                //increase incorrect counter
                ++incorrectGuesses;

                //update hangman image
                CustomTools.updateImage(hangmanImage, "resources/" + (incorrectGuesses + 1) + ".png");

                //user failed to guess the word

            }
        }
    }

    private void resetGame(){
        //load new challenge
        wordChallenge = wordDB.loadChallenge();
        incorrectGuesses = 0;

        //load starting image
        CustomTools.updateImage(hangmanImage, CommonConstants.IMAGE_PATH);

        //update category
        categorylabel.setText(wordChallenge[0]);

        //update hidden word
        String hiddenWord = CustomTools.hiddenWords(wordChallenge[1]);
        hiddenWordLabel.setText(hiddenWord);

        //enable all buttons again
        for(int i = 0; i < letterButtons.length; i++){
            letterButtons[i].setEnabled(true);
            letterButtons[i].setBackground(CommonConstants.PRIMARY_COLOR);
        }
    }
}
