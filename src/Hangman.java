import javax.swing.*;

public class Hangman extends JFrame {
    //counts the number of incorrect guesses made by the player
    private int incorrectGuesses;

    //store the challenge from the WordDB here
    private String[] wordChallenge;

    private final WordDB wordDB;

    private JLabel hangmanImage;

    public Hangman(){
        super("Hangman Game (Java Ed.");
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

        getContentPane().add(hangmanImage);
    }
}
