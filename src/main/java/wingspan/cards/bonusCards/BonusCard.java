package wingspan.cards.bonusCards;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import wingspan.core.Player;

public class BonusCard {
	private BufferedImage cardImage;
	
	public BonusCard(String imagePath) throws IOException
	{
		InputStream inputStream = BonusCard.class.getResourceAsStream("/Images/" + imagePath);
        this.cardImage = ImageIO.read(inputStream);
		System.out.println("The BonusCard " + imagePath + " was created");
	}
	
	public BufferedImage getImage()
	{
		return cardImage;
	}

	public int calculateScore(Player p)
	{
		//placeholder; will be overridden in child BonusCard classes
		return 0;
	}

	public int calculateCards(int numCards, int higher, int lower, int higherPoints, int lowerPoints)
	{
		if (numCards >= higher)
		{
			return higherPoints;
		}
		else if (numCards >= lower)
		{
			return lowerPoints;
		}
		else
		{
			return 0;
		}
	}
}

