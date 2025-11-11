package wingspan.core;

import wingspan.cards.Card;
import java.util.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

public class GameBoard {
	public BufferedImage gameBoardImage;
	private List<Card> forest;
	private List<Card> grasslands;
	private List<Card> wetlands;
	
	public GameBoard() throws IOException
	{
		gameBoardImage = ImageIO.read(GameBoard.class.getResource("/Image/GameBoard.jpg"));
		forest = new ArrayList<>();
		grasslands = new ArrayList<>();
		wetlands = new ArrayList<>();
	}
	
	public boolean addCard(Card c, String habitat)
	{
		if (habitat.equals("forest"))
		{
			if (forest.size() == 5)
				return false;
			forest.add(c);
		}
		else if (habitat.equals("grasslands"))
		{
			if (grasslands.size() == 5)
				return false;
			grasslands.add(c);
		}
		else
		{
			if (wetlands.size() == 5)
				return false;
			wetlands.add(c);
		}
		return true;
	}
}
