package wingspan;
import java.io.IOException;

import javax.swing.JFrame;

import wingspan.ui.MainPanel;

public class WingspanFrame extends JFrame{
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

    public WingspanFrame(String name) throws IOException{
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);

        //Display panels based on conditionals checking which state of the game is in. Ex: isSetup = true, display the SetupPanel
        // add(new IntroPanel());
        add(new MainPanel());
        setVisible(true);
    }
}

