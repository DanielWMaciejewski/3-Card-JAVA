import java.util.Scanner;
//Daniel Maciejewski
//Final: Design a program to emulate a deck of cards to be used to play 3-card Poker
public class threeCardPoker {

	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);
		final int sizeOfDeck = 36;//set the size of the deck array
		int[] deck = new int[sizeOfDeck];//instantiate array
		int[] initializedDeck = new int[sizeOfDeck];//instantiate array
		initializedDeck = initDeck(deck);//crack open a fresh deck
		int[] shuffledDeck = new int[sizeOfDeck];//reserve an array to hold a shuffled deck of cards
		int nHands = 0;//count the number of hands played
		boolean play = false;//signify whether or not user wants to play
		int sizeOfHand = 3;//specify the size of the player and opponent hand arrays
		int[] playerHand = new int[sizeOfHand];//initialize a player's hand
		int[] opponentHand = new int[sizeOfHand];//initialize an opponent's hand
		int[] playerSorted = new int[sizeOfHand];//initialized array to hold sorted player hand
		int[] opponentSorted = new int[sizeOfHand];//initialized array to hold sorted opponent hand
		int pWon = 0;//count wins
		System.out.print("Welcome to 3 Card Poker\r\n" + 
				"++++++++++++++++++++++++++++++++++++++++++++++\n");
		do {

		play = playHand(userInput);//hold's user's play choice
		
		if (play == true) {
			nHands++;//increment the number of hands played
			int playerIndex = 0,opponentIndex = 0;//positional tracking for indexing player and opponent arrays
			shuffledDeck = shuffleDeck(initializedDeck);//slap those cards around a bit
			displayHand(shuffledDeck);//show user the hands
			for(int i = 0;i<5;i+=2) {//populate player hand
				playerHand[playerIndex]=shuffledDeck[i];playerIndex++;
				}//end for
			
			for(int j = 1;j<6;j+=2) {//populate opponent hand
				opponentHand[opponentIndex]=shuffledDeck[j];opponentIndex++;
				}//end for
			
			playerSorted = sortHand(playerHand);//holds the sorted player hand
			opponentSorted = sortHand(opponentHand);//holds the sorted opponent hand
			
			
			
			boolean playerStraight = isStraight(playerHand);//returns true if player has a straight
			boolean opponentStraight = isStraight(opponentHand);//returns true if opponent has a straight
			
			boolean playerFlush = isFlush(playerHand);//returns true if player has a flush
			boolean opponentFlush = isFlush(opponentHand);//returns true if opponent has a flush
			
			//this cluster determines if the player and opponent have a bubkiss
			boolean playerBubkiss = false;
			if(playerStraight == false && playerFlush == false) {playerBubkiss = true;}
				
			boolean opponentBubkiss = false;
			if (opponentStraight == false && opponentFlush == false) {opponentBubkiss = true;}
			
			//(win/loss)-state logic
			if(playerFlush && opponentStraight){pWon++;System.out.println("Player hand is a Flush! Opponent hand is straight. You won!");}//PF>OS pWin
			else if(playerStraight && opponentFlush) {System.out.println("Opponent hand is a Flush! Opponent hand is Flush.You lose!");}//PS<OF
			else if(opponentBubkiss == true && playerStraight == true) {pWon++;System.out.println("Player hand is a Straight! Opponent has a Bubkiss. You won!");}//PS>OB pWin
			else if(opponentBubkiss == true && playerFlush == true) {pWon++;System.out.println("Player hand is a Flush! Opponent has a Bubkiss. You won!");}//PF>OB pWin
			else if(playerBubkiss && opponentFlush){System.out.println("Opponent Flush beats your Bubkiss You lose!");}//PB<OF
			else if(playerBubkiss && opponentStraight) {System.out.println("Opponent Straight beats your Bubkiss You lose!");}//PB<OS
			else if(playerBubkiss == true && opponentBubkiss == true) {System.out.println("Player and Opponent both Bubkiss. It's a tie.");}//PB=OB
			else if(playerFlush == true && opponentFlush == true) {System.out.println("Player and Opponent both Flush. It's a tie.");}//PF=OF
			else{System.out.println("Player and Opponent both Straight. It's a tie.");}//PS=OS
			
			}//end if
		
		else {play = false;}//player opts to quit
		
		}while(play == true);
		
		report(nHands, pWon);//print the players wins and hands
		userInput.close();//close resources
		
	}//end main
	
	public static boolean playHand(Scanner userInput) {
		
		String choice = "";//holds choice
		boolean choiceFlag = false;//checks user input using this flag
		do {
			
			System.out.println("Play a hand [ y / n ] ?");
			choice = userInput.next();//get user input
			if(choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("n")){choiceFlag = false;}//user input is acceptable
			else 
			{choiceFlag = true;}//ask again
		}while(choiceFlag);
		
		if(choice.equalsIgnoreCase("y")) {return true;}//player opts to play
		else
		{return false;}
		
	}//end playHand
	
	public static void displayHand(int[] deck) {
		
		int sizeOfHand = 3;//define hand size
		int[] playerHand = new int[sizeOfHand];// create player hand array
		int[] opponentHand = new int[sizeOfHand];// create opponent hand array
		int[] playerSorted = new int[sizeOfHand];// create player sorted hand array
		int[] opponentSorted = new int[sizeOfHand];// create opponent sorted hand array
		int playerIndex = 0,opponentIndex = 0;
		
		for(int i = 0;i<5;i+=2) {//populate player hand
			playerHand[playerIndex]=deck[i];playerIndex++;
			}//end for
		
		for(int j = 1;j<6;j+=2) {//populate opponent hand
			opponentHand[opponentIndex]=deck[j];opponentIndex++;
			}//end for
		//organize hand arrays highest to lowest
		playerSorted = sortHand(playerHand);
		opponentSorted = sortHand(opponentHand);
		//display hands
		System.out.println("\nPlayer's Hand");
		for(int j = 0;j<playerHand.length;j++) {System.out.print(cardValue(playerHand[j])+" of "+cardSuit(playerHand[j])+" ");}
		System.out.println("\n\nOpponent's Hand");
		for(int k = 0;k<playerHand.length;k++) {System.out.print(cardValue(opponentHand[k])+" of "+cardSuit(opponentHand[k])+" ");}
		System.out.println("\n");
		
	}//end displayHand
	
public static int cardValue(int card) {//resolve the numeric value of a given card
	
	return card%9+1;
	
}//end cardValue

public static String cardSuit(int card) {//attach a suit value to a given card 
	
	String suit = "";
	if(card/9 == 0) {return suit = "Clubs";}
	else if (card/9 == 1) {return suit = "Spades";}
	else if (card/9 == 2) {return suit = "Hearts";}
	else {return suit = "Diamonds";}
	
}//end cardSuit

public static int[] initDeck(int[] deck) {//populates the array deck such that it's index is it's index's value
	
	for(int i = 0;i<deck.length;i++) {
		deck[i]=i;
	}//end for
	
	return deck;

}//end initDeck

public static void cutDeck(int[] deck) {//generate a random cut value between 6 and 24, inclusive, and use it to divide the deck array in two, then flip halves and 
										//stick them back together
	int cut = 6+(int)(Math.random()*(19));
	int[] top = new int[cut];
	int[] bottom = new int[36-cut];
	int bottomIndex = 0;
	int topIndex = 0;

	for(int i=0;i<=cut-1;i++) {//top
		top[i]=deck[i];
		}//end for

	for(int i=cut;i<=35;i++) {//bottom
		bottom[bottomIndex]=deck[i];bottomIndex++;
		}//end for

	for(int i =0;i<=35-cut;i++) {//repopulate deck
		deck[i]=bottom[i];
		}//end for
	
	for(int i = 35-cut+1;i<=35;i++) {//repopulate deck
		deck[i]=top[topIndex];topIndex++;
		}
	
}//end cutDeck

public static int[] shuffleDeck(int [] deck) {//given a choice n of iterations, cut and shuffle the deck
	int top[] = new int[18];
	int bottom[] = new int[18];
	int n = 5+(int)Math.random()*15;
	
	System.out.println("Shuffling Deck...");
	
	while(n != 0){	
		int topI = 0;
		int botI = 0;
		int bottomIndex = 0;
		cutDeck(deck);
		n--;
		
		for(int i = 0;i<18;i++) {//populate array top
			top[i]=deck[i];
			}//end for
		
		for(int i = 18;i<36;i++) {//populate array bottom
			bottom[bottomIndex]=deck[i];bottomIndex++;
			}//end for
		
		for(int i = 0;i <36;i++) {//repopulate deck
			if(i==0) {deck[i]=top[0];topI++;}
			else if(i%2==0){deck[i]=top[topI];topI++;} 
			else {deck[i]=bottom[botI];botI++;}
			}//end for
		
	}//end while

	return deck;
	
}//end shuffleDeck

public static boolean isFlush(int[] hand) {//determines if a hand is a Flush(all same face)
	if(cardSuit(hand[0]) == cardSuit(hand[1]) && cardSuit(hand[0]) == cardSuit(hand[2])) 
	{return true;}
	else
	{return false;}
	
}//end isFlush

public static boolean isStraight(int[] hand) {//determines if a hand is a Straight (sequential numbers)
	if(cardValue(hand[0]) == cardValue(hand[1]+1) && cardValue(hand[0]) == cardValue(hand[2]+2)) 
	{return true;}
	else 
	{return false;}
	
}//end isStraight

public static void report(int nHands, int pWon) {//tell the player how many hands they played and how many they won
	
	System.out.println("Thanks for playing ...\r\n" + 
			"You played a total of "+nHands+" hand(s)\r\n" + 
			"In the end you won "+pWon+" of them");
	
}//end report

public static int[] sortHand(int[] hand){//sort hand highest numeric value to lowest
	
	for (int i = 0; i < hand.length; ++i)
	{
		int maxLoc = i;

		for (int j = i+1; j < hand.length; ++j)
			if (cardValue(hand[j]) > cardValue(hand[maxLoc]))
				maxLoc = j;
		int tmp = hand[i];
		hand[i] = hand[maxLoc];
		hand[maxLoc] = tmp;
	}//end for
	
	return hand;

}//end sortHand 

}//end class