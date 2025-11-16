package wingspan.cards.goals;
import java.awt.image.BufferedImage;
import wingspan.core.Player;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.*;

public class Goal {

    private final BufferedImage goalImage; // what the player will be shown

    public Goal(String imagePath) throws IOException
    {
        InputStream inputStream = Goal.class.getResourceAsStream("/Images/" + imagePath);
        this.goalImage = ImageIO.read(inputStream);
        System.out.println("The Goal " + imagePath + " was created.");
    }

    public int evaluatePlayer(Player p)
    {
        /*
         *  
         * 
         * if goal met:
         *      player points + amount
         *  else:
         *      no points added (or points deducted depending on whether that is a rule)
         * 
         * 
         */

        return 0; // arbitrary return value for now
    }

    public int getPlayerScore(Player p) // returns the number of points a player would've scored for a goal if using the blue goalboard
    {
        return Math.min(evaluatePlayer(p), 5);
    }

    public ArrayList<Player[]> getPlayerRankings(ArrayList<Player> players) // returns an ArrayList of player arrays in order of how much they fulfilled the goal, tied players are in the same ArrayList. This method would be used for the green goalboard.
    {
        HashSet<Integer> totalUniqueScores = new HashSet<Integer>();
        for(Player p: players)
        {
            totalUniqueScores.add(evaluatePlayer(p));
        }
        ArrayList<Player[]> rankings = new ArrayList<>();
        ArrayList<Player> tempPlayerList = new ArrayList<>();
        for(Player p: players)
        {
            tempPlayerList.add(p);
        }
        for(int i=0; i<totalUniqueScores.size(); i++)
        {
            int maxScore = -1;
            int playersWithMaxScore = 0;
            for(Player p: tempPlayerList)
            {
                if (evaluatePlayer(p) > maxScore)
                {
                    maxScore = evaluatePlayer(p);
                }
            }
            for (Player p: tempPlayerList)
            {
                if (evaluatePlayer(p) == maxScore)
                {
                    playersWithMaxScore++;
                }
            }
            Player[] highestPlayers = new Player[playersWithMaxScore];
            int index = 0;
            for(Player p: tempPlayerList)
            {
                if (evaluatePlayer(p) == maxScore)
                {
                    highestPlayers[index] = p;
                    tempPlayerList.remove(p);
                    index++;
                }
            }
            rankings.add(highestPlayers);
        }
        return rankings;
    }

    public BufferedImage getImage()
    {
        return this.goalImage;
    }

}
// Goals: 
// - Egg in certain habitat
// - Card in certain habitat
// - Eggs on certain nests
// - Birds with certain nests that have eggs
// - sets of 3 eggs
// - Total birds
