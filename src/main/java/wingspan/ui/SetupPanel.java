package wingspan.ui;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.io.*;
import java.util.*;
import wingspan.cards.*;
import wingspan.cards.bonusCards.BonusCard;

import static wingspan.core.GameState.cardManager;
import static wingspan.core.GameState.players;

public class SetupPanel extends JPanel implements KeyListener, MouseListener{

    private boolean birdCardsAreSet; //Boolean to check if the current player has gotten their bird and food tokens
    private boolean playerIsSet;// Boolean to check if the current player has completed the setup stage

    private int numSelected; //Total number of items (Bird/Bonus cards / Food Tokens) selected
    private int currentPlayer; // The current player selecting their cards (1, 2, 3, 4)

    private ArrayList<Card> cards; //Collection used to store the Bird cards drawn from the deck
    private ArrayList<Card> selectedCards; // Collection used to store the Bird cards selected by the player

    private ArrayList<BonusCard> bonusCards; // Collection used to store the Bonus cards drawn from the deck
    private ArrayList<BonusCard> selectedBonusCards; // Collection used to store the Bonus cards selected by the player

    //create a collection for the Food tokens
    //create a collection for the selected Food tokens

    public SetupPanel() {

        //This is mostly just initialization, nothing of interest here

        birdCardsAreSet = false;
        playerIsSet = false;

        currentPlayer = 1;
        numSelected = 0;

        selectedCards = new ArrayList<>();
        selectedBonusCards = new ArrayList<>();

        cards = new ArrayList<>();
        drawFiveBirds(cards);

        bonusCards = new ArrayList<>();
        drawTwoBonuses(bonusCards);

		addMouseListener(this);
		addKeyListener(this);
	}
    
    public void addNotify(){
        super.addNotify();
        requestFocus();
    }


    public void paint(Graphics g) {
    	super.paint(g);
		g.setColor(new Color(0, 255, 150));
		
        if(!birdCardsAreSet){ //Checks if the player has selected and drawn Bird cards (and Food tokens) - if they haven't, paint the Bird cards and Food tokens.
            for(Card card: cards){
				if(selectedCards.contains(card)){
                	g.fillRect(208 + cards.indexOf(card) * 315, 208, 244, 364); // If a card is selected, first paint a green rectangle underneath the card. Then put the card image. This will create the outline of the card. The ouline will be 2 px wide/tall.
				}
				g.drawImage(card.getCardImage(), 210 + cards.indexOf(card) * 315, 210, 240, 360, null ); //Spacing between cards: 75 px | Horizontal Spacing on the margins: 210 px | Card Width: 240 px | Card Height: 360 px
            }
			
            //Draw the Text
            //Draw food token images
        }

        if(birdCardsAreSet && !playerIsSet){ //Checks if the player has drawn Bird Cards (and Food tokens) but hasn't completed the setup phase (Or hasn't drawn bonus cards) - if they have drawn their Bird cards but haven't completed the setup stage, paint the bonus cards.
            for(BonusCard bonusCard: bonusCards){
				if(selectedBonusCards.contains(bonusCard)){
					g.fillRect(358 + bonusCards.indexOf(bonusCard) * 780, 208, 424, 634); // Essentially the same thing as line 71 
				}
                g.drawImage(bonusCard.getImage(), 360 + bonusCards.indexOf(bonusCard) * 780, 210, 420, 630, null); // Spacing between cards: 360 px | Horizontal Spacing on the Margins: 360 px | Card Width: 420 px | Card Height: 630 px
            }
        }
        
        //Implement a way to show which card/food token the player is selecting (a green outline is a bit complicated so I'll simply do a green circle underneath the selected item(s))
    }


    public void keyPressed(KeyEvent e){
        char c = e.getKeyChar();

        if(c == 'c'){ //Check if the current player has pressed 'c' to confirm their selection
            if(!birdCardsAreSet){ //Check if the current player is selecting Bird Cards/ Food tokens
                if(numSelected == 5){ //Check if the player meets the requirements for total Bird/Food items - if they have, continue
                    for(Card card: selectedCards) //Give all selected cards to the player
                        players.get(currentPlayer-1).addCard(card);

                    //Add code to add food items to the player, this hasn't been implemented because I don't know how enums work :/

                    birdCardsAreSet = true; //Switch the player's state of having selected their bird/food items
                    numSelected = 0; //Reset the number of items selected
                    repaint(); // Redraw the panel/screen
                }
                if(numSelected != 5){ //This code is yet to be completed, but it will check if the player is trying to confirm their selection when they don't meet the requirements
                   
                }
            }
            if(birdCardsAreSet && !playerIsSet) { //Checks if the player is selecting Bird Cards and Food tokens but hasn't completed the setup phase (Or hasn't drawn bonus cards)
                if(numSelected == 1){ //Check if the player meets the requirements for total Bonus Cards
                    players.get(currentPlayer-1).addBonusCard(selectedBonusCards.get(0)); //Give the selected Bonus card to the Player
                    
                    playerIsSet = true; //Switch the player's state of having completed the setup stage
                    currentPlayer += 1; //Switch to the next player
                    
                    if(currentPlayer > players.size() + 1){ //Check if the last player has confirmed their setup
                        //change isSetup to false in order to deactivate this panel
                    }
                    
                    while(playerIsSet) { //Might be redundant but whatever (Note: I'm just realizing but the variable playerIsSet might be redundant all together)
                        numSelected = 0; //Reset the number of items selected

                        selectedCards = new ArrayList<>(); //Reset selected Bird cards
                        selectedBonusCards = new ArrayList<>(); //Reset selected Bonus Cards
                        //Add code to reset the Food collection
                        
                        cards = new ArrayList<>(); //Reset the drawn Bird Cards
                        bonusCards = new ArrayList<>(); //Reset the draw Bonus Cards
                        
                        drawFiveBirds(cards); //Add 5 new Bird Cards
                        drawTwoBonuses(bonusCards); //Add 2 new Bonus Cards
                        
                        playerIsSet = false;
                    }
                    
                    repaint();
                }
                if(numSelected != 1){ //This code is yet to be completed, but it will check if the player is trying to confirm their selection when they don't meet the requirements
                    
                }
            }
        }
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}


  	public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if(!birdCardsAreSet){ //Check if the player is selecting Bird/Food tokens
            if(y <= 570 && y >= 210){ //This Y-Level represents the Bird Cards - The conditional checks whether the player is selecting from here
                for(int i = 0; i < cards.size(); i++){
                    if(x >= 210 + i * 315 && x <= 450 + i * 315) { //A for loop is created to check what card the player has selected

                        if(!selectedCards.contains(cards.get(i))) { //If the selected card is not in the selected cards list of the player
                            selectedCards.add(cards.get(i)); //Add the selected card to the selected cards list
                            numSelected += 1; //Increase the number of selected items by 1
                            repaint(); //Redraw
                        }

                        if(selectedCards.contains(cards.get(i))){ //If the selected card is in the selected cards list i.e. the player wants to deselect
                            selectedCards.remove(cards.get(i)); //Remove the selected card from the selected cards list
                            numSelected -= 1; //Decrease the number of items selected
                            repaint(); //Redraw
                        }
                    }
                }
            }

            //Add code here to determine whether the player is selecting from the Y-Level of the food tokens and add/deselect appropriately
        }

        if(birdCardsAreSet && !playerIsSet){ //Checks if the player has drawn Bird Cards and Food tokens but hasn't completed the setup phase (Or hasn't drawn bonus cards)
            if(y <= 840 && y >= 210){ //Check Y-Level, the Y level of the Bonus Cards should be between 210 and 840
                for(int i = 0; i < bonusCards.size(); i++){
                    if(x >= 360 + i * 780 && x <= 780 + i * 780){ //A for loop is used to check which Bonus card is being selected
                        
                        if(!selectedBonusCards.contains(bonusCards.get(i))){ //If the Bonus card is not already selected
                            selectedBonusCards.add(bonusCards.get(i)); //Add it to the selected Bonus card list
                            numSelected += 1; //Increase the number of items selected by 1
                            repaint(); //Redraw
                        }
                        
                        if(selectedBonusCards.contains(bonusCards.get(i))){ //If the Bonus card is already selected i.e. the player wants to deselect
                            selectedBonusCards.remove(bonusCards.get(i)); //Remove it from the selected Bonus Cards list
                            numSelected -= 1; //Decrease the number of items selected by 1
                            repaint(); //Redraw
                        }
                    }
                }
            }
        }
    }
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}


    public static void drawFiveBirds(ArrayList<Card> list){ //Draws 5 bird cards from the deck -- This isn't planned to be reused after the setup stage
        for(int i = 0; i < 5; i++)
            list.add(cardManager.getRandomCard());
    }

    public static void drawTwoBonuses(ArrayList<BonusCard> list){ //Draws 2 Bonus cards from the deck -- This isn't planned to be reused after the setup stage
        for(int i = 0; i < 2; i++)
            list.add(cardManager.getRandomBonusCard());
    }
}
