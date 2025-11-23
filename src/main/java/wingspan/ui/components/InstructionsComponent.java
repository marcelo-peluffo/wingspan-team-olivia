package wingspan.ui.components;
import java.awt.*;
import javax.swing.*;

public class InstructionsComponent extends JPanel{
    public InstructionsComponent()
    {

    }

    public void paint(Graphics g)
    {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(getWidth() - 200, 0, 200, 200);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.drawString("Controls", getWidth() - 140, 25);
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.drawString("'p': Play a bird", getWidth() - 200, 50);
        g.drawString("'f': Gain Food", getWidth() - 200, 75);
        g.drawString("'e': Lay Eggs", getWidth() - 200, 100);
        g.drawString("'d': Draw Cards", getWidth() - 200, 125);
        g.drawString("Click on any card on the", getWidth() - 200, 160);
        g.drawString("screen to zoom in on it", getWidth() - 200, 175);
    }
}
