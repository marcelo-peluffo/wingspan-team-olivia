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
	private Habitat activeHabitat;
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

	public boolean removeCard(Habitat h)
	{
		switch (h)
		{
			case FOREST: forest.remove(forest.size()-1); break;
			case GRASSLANDS: grasslands.remove(grasslands.size()-1); break;
			case WETLANDS: wetlands.remove(wetlands.size()-1); break;
		}
		return true;
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

	public List<Card> getActiveHabitat() {
		switch (activeHabitat)
		{
			case FOREST -> {return forest;}
			case GRASSLANDS -> {return grasslands;}
			case WETLANDS -> {return wetlands;}
		}

		return null;
	}

	public void setActiveHabitat(Habitat h)
	{
		activeHabitat = h;
	}
	public ArrayList<Card> returnAllCards()
	{
		ArrayList<Card> list = new ArrayList<Card>();
		for(Card c: forest)
		{
			list.add(c);
		}
		for(Card c: grasslands)
		{
			list.add(c);
		}
		for(Card c: wetlands)
		{
			list.add(c);
		}
		return list;
	}

	public List<Card> getCardsInHabitat(Habitat h) {
		switch (h) {
			case FOREST -> {return forest;}
			case GRASSLANDS -> {return grasslands;}
			case WETLANDS -> {return wetlands;}
		}

		return null;
	}

	public int numEggsAt(int indexOfGrasslands) {
		switch (indexOfGrasslands) {
			case 0: return 2;
			case 1: return 2;
			case 2: return 3;
			case 3: return 3;
			case 4: return 4;
			case 5: return 4;
		}

		return 0;
	}
}
