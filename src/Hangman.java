import javax.swing.*;
import java.awt.*;

public class Hangman extends JFrame {
    //counts the number of incorrect guesses made by the player
    private int incorrectGuesses;

    //store the challenge from the WordDB here
    private String[] wordChallenge;

    private final WordDB wordDB;

    private JLabel hangmanImage, categorylabel;

    public Hangman(){
        super("Hangman Game (Java Ed.)");
        setSize(CommonConstants.FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        //initiating vars
        wordDB = new WordDB();
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
        categorylabel.setForeground(Color.white);
        categorylabel.setBackground(CommonConstants.SECONDARY_COLOR);
        categorylabel.setBorder(BorderFactory.createLineBorder(CommonConstants.SECONDARY_COLOR));
        categorylabel.setBounds(
                0,
                hangmanImage.getPreferredSize().height - 20,
                hangmanImage.getPreferredSize().width,
                categorylabel.getPreferredSize().height
        );

        getContentPane().add(categorylabel);
        getContentPane().add(hangmanImage);
    }
}
