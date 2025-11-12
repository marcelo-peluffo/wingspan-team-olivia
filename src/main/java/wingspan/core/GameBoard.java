package wingspan.core;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import wingspan.cards.Card;
import wingspan.enums.Habitat;

public class GameBoard {
	public BufferedImage gameBoardImage;
	private List<Card> forest;
	private List<Card> grasslands;
	private List<Card> wetlands;
	
	public GameBoard() throws IOException
	{
		gameBoardImage = ImageIO.read(GameBoard.class.getResource("/Images/GameBoard.jpg"));
		forest = new ArrayList<>();
		grasslands = new ArrayList<>();
		wetlands = new ArrayList<>();
	}
	
	public boolean addCard(Card c, Habitat habitat)
	{
            switch (habitat) {
                case FOREST -> {
                    if (forest.size() == 5)
                        return false;
                    forest.add(c);
                }
                case GRASSLANDS -> {
                    if (grasslands.size() == 5)
                        return false;
                    grasslands.add(c);
                }
                default -> {
                    if (wetlands.size() == 5)
                        return false;
                    wetlands.add(c);
                }
            }
		return true;
	}

    public List<Card> getForest() {
        return forest;
    }

    public List<Card> getGrasslands() {
        return grasslands;
    }

    public List<Card> getWetlands() {
        return wetlands;
    }
}
