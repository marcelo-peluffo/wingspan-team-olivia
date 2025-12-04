package wingspan.food;
import java.util.*;
import java.io.*;
import wingspan.enums.*;

public class FoodManager {
	private ArrayList<FoodDice> birdFeeder;
	private ArrayList<FoodDice> usedDice;
	
	public FoodManager() throws IOException
	{
		birdFeeder = new ArrayList<FoodDice>();
		usedDice = new ArrayList<FoodDice>();
		reroll();
	}
	
	public void reroll() throws IOException
	{
		birdFeeder.clear();
		for(int i=0; i<5; i++)
		{
			birdFeeder.add(new FoodDice());
		}
		usedDice.clear();
	}
	
	public FoodDice getDie(int index)
	{
		FoodDice die = birdFeeder.get(index);
		usedDice.add(die);
		birdFeeder.remove(index);
		return die;
	}

	public FoodDice seeDie(int index)
	{
		return birdFeeder.get(index);
	}

	public ArrayList<FoodDice> getUsedDice()
	{
		return usedDice;
	}

	public ArrayList<FoodDice> getBirdFeeder()
	{
		return birdFeeder;
	}

	public boolean hasFood(Food f)
	{
		for(FoodDice fd: birdFeeder)
		{
			if (fd.getFood() == f)
			{
				return true;
			}
		}
		return false;
	}
}
