package wingspan.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import wingspan.core.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameEndPanel extends JPanel implements KeyListener {

	private BufferedImage scoreSheetImage, background;
    private Player winner;
    private ArrayList<int[]> scores;
	
	public GameEndPanel() {
		try {
			scoreSheetImage = ImageIO.read(GameEndPanel.class.getResource("/Images/ScoreSheet.jpg"));
			background = ImageIO.read(GameEndPanel.class.getResource("/Images/backgroundImage2.jpeg"));

		}catch (Exception e) {
			System.out.println("Error");
			return;
        }
        scores = new ArrayList<>();
        for(Player p: GameState.players)
        {
            scores.add(p.getAllScores());
        }
        int highest = -1;
        for(int i=0; i<4; i++)
        {
            if (scores.get(i)[6] > highest)
            {
                highest = scores.get(i)[6];
                winner = GameState.players.get(i);
            }
        }
		addKeyListener(this);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(background, 0,0, 1920, 1080, null);
		g2d.drawImage(scoreSheetImage, 500, 150, 900, 700,null);
		g2d.setColor(Color.YELLOW);
		g2d.fillRect(675, 50, 500, 75);
		g2d.setColor(Color.black);
		Font current = g2d.getFont();
		Font newFont = current.deriveFont(40F);
		g2d.setFont(newFont);
		g2d.drawString("Player " + (GameState.players.indexOf(winner) + 1 ) + " has won!!", 725, 100);
        g2d.setColor(new Color(19,175,87));
        g2d.fillRect(600, 890, 700, 50);
		g2d.setColor(Color.white);
		g2d.drawString("Press \"r\" to play again", 775, 925);
        g2d.setColor(Color.GRAY);
        g2d.fillRect(500, 950, 900, 50);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Press 't' to switch between this screen, the goal board, and the players' boards", 600, 980);
        int xPos = 825;
        int yPos = 275;
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 25));
        for(int i=0; i<4; i++)
        {
            int[] playerScores = scores.get(i);
            for(int score: playerScores)
            {
                g2d.drawString("" + score, xPos, yPos);
                yPos += 90;
            }
            yPos = 275;
            xPos += 120;
        }
        xPos = 793;
        yPos = 195;
        for(int i=1; i<5; i++)
        {
            g2d.drawString("Player " + i, xPos, yPos);
            xPos += 120;
        }
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		char a = e.getKeyChar();
		if(a == 'r') {
			System.out.println("Restart");
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}