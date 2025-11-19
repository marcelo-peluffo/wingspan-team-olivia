package wingspan.core;
import wingspan.cards.goals.*;
import wingspan.enums.*;

import java.io.IOException;
import java.util.*;

public class GoalBoard {
	private List<Goal> goals;
	private Goal round1Goal;
	private Goal round2Goal;
	private Goal round3Goal;
	private Goal round4Goal;
	
	public GoalBoard() throws IOException
	{
		goals = new ArrayList<>();
		addGoals();
		round1Goal = goals.remove((int)(Math.random() * goals.size()));
		round2Goal = goals.remove((int)(Math.random() * goals.size()));
		round3Goal = goals.remove((int)(Math.random() * goals.size()));
		round4Goal = goals.remove((int)(Math.random() * goals.size()));
	}
	
	public void addGoals() throws IOException
	{
		goals.add(new HabitatGoal(Habitat.FOREST, false, "EggsInForestGoal"));
		goals.add(new HabitatGoal(Habitat.GRASSLANDS, false, "EggsInGrasslandsGoal"));
		goals.add(new HabitatGoal(Habitat.WETLANDS, false, "EggsInWetlandGoal"));
		goals.add(new HabitatGoal(Habitat.FOREST, true, "BirdsInForestGoal"));
		goals.add(new HabitatGoal(Habitat.GRASSLANDS, true, "BirdsInGrasslandGoal"));
		goals.add(new HabitatGoal(Habitat.WETLANDS, true, "BirdsInWetlandsGoal"));
		goals.add(new EggsNestGoal(NestType.BOWL, false, "BowlCardsWithEggGoal"));
		goals.add(new EggsNestGoal(NestType.CAVITY, false, "CavityCardsWithEggsGoal"));
		goals.add(new EggsNestGoal(NestType.PLATFORM, false, "PlatformCardsWithEggGoal"));
		goals.add(new EggsNestGoal(NestType.GROUND, false, "GroundCardsWithEggsGoal"));
		goals.add(new EggsNestGoal(NestType.BOWL, true, "EggsInBowlGoal"));
		goals.add(new EggsNestGoal(NestType.CAVITY, true, "EggsInCavityGoal"));
		goals.add(new EggsNestGoal(NestType.PLATFORM, true, "EggsInPlatformGoal"));
		goals.add(new EggsNestGoal(NestType.GROUND, true, "EggsInGroundGoal"));
		goals.add(new WildGoal(true, "TotalCardsGoal"));
		goals.add(new WildGoal(false, "SetOfEggsGoal"));
	}

	public Goal[] getGoals()
	{
		Goal[] goals = {round1Goal, round2Goal, round3Goal, round4Goal};
		return goals;
	}
}
