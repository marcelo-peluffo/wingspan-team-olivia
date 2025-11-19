package wingspan;
import javax.swing.*;
import wingspan.ui.*;
import wingspan.ui.components.*;

public class WingspanFrame extends JFrame{
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

    public WingspanFrame(String name){
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setResizable(false);

        //Display panels based on conditionals checking which state of the game is in. Ex: isSetup = true, display the SetupPanel
        add(new NavigatorComponent());
    }
}

