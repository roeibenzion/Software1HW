package il.ac.tau.cs.sw1.ex4;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class WordPuzzle {
	public static final char HIDDEN_CHAR = '_';
	public static final int MAX_VOCABULARY_SIZE = 3000;
	
	/*
	 * @pre: template is legal for word
	 */
	public static char[] createPuzzleFromTemplate(String word, boolean[] template) { // Q - 1
		char [] riddle = new char [word.length()];
		for (int i = 0; i < word.length(); i++) {
			if(!template[i])
				riddle[i] = word.charAt(i);
			else
				riddle[i] = HIDDEN_CHAR;
		}
		return riddle;
	}

	public static boolean checkLegalTemplate(String word, boolean[] template) { // Q - 2
		if((word.length() != template.length) || !checkMin(template))
			return false;
		for (int i = 0; i < word.length(); i++) {
			for (int j = i; j < template.length; j++) {
				if(word.charAt(i) == word.charAt(j) && template[i] != template[j])
					return false;
			}
		}
		return true;
	}
	private static boolean checkMin(boolean[] template)
	{
		int i = 0;
		boolean hide = false, exp = false;
		while((!hide || !exp) && i < template.length)
		{
			if(!hide && template[i])
				hide = true;
			if(!exp && !template[i])
				exp = true;
			i++;
		}
		return (hide&&exp);
	}
	/*
	 * @pre: 0 < k < word.lenght(), word.length() <= 10
	 */
	public static boolean[][] getAllLegalTemplates(String word, int k){  // Q - 3
		int n = (int) Math.pow(2, word.length());
		String bin;
		boolean [] templates = new boolean [word.length()];
		boolean [][] allTemplates = new boolean[n][word.length()];
		int row = 0;
		for (int i = 0; i < n; i++) {
			bin = Integer.toBinaryString(i);
			templates = Arrays.copyOf(getTemplateOutOfBin(bin, templates), templates.length);
			if(countTrue(templates) == k && checkLegalTemplate(word, templates))
			{
				allTemplates[row] = Arrays.copyOf(templates, templates.length);
				row++;
			}
		}
		boolean [][] legalTemplates = new boolean [row][word.length()];
		for (int i = 0; i < row; i++) {
			legalTemplates[i] = Arrays.copyOf(allTemplates[i], word.length()); 
		}
		return legalTemplates;
	}
	
	private static boolean[] getTemplateOutOfBin(String bin, boolean [] template)
	{
		int rem = template.length - bin.length();
		for (int i = 0; i < rem; i++) 
			bin = '0' + bin;
		
		for (int i = 0; i < bin.length(); i++) {
			if(bin.charAt(i) == '1')
				template[i] = true;
			else
				template[i] = false;
		}
		return template;
	}
	private static int countTrue(boolean [] template)
	{
		int count = 0;
		for (int i = 0; i < template.length; i++) {
			if(template[i])
				count++;
		}
		return count;
	}
	/*
	 * @pre: puzzle is a legal puzzle constructed from word, guess is in [a...z]
	 */
	public static int applyGuess(char guess, String word, char[] puzzle) { // Q - 4
		int count = 0;
		for (int i = 0; i < word.length(); i++) {
			if(word.charAt(i) == guess && puzzle[i] == HIDDEN_CHAR)
			{
				while(i < word.length())
				{
					if(word.charAt(i) == guess)
					{
						puzzle[i] = guess;
						count++;
					}
					i++;
				}
				break;
			}
		}
		return count;
	}
	/*
	 * @pre: puzzle is a legal puzzle constructed from word
	 * @pre: puzzle contains at least one hidden character. 
	 * @pre: there are at least 2 letters that don't appear in word, and the user didn't guess
	 */
	public static char[] getHint(String word, char[] puzzle, boolean[] already_guessed) { // Q - 5
		
		Random rnd = new Random();
		rnd.setSeed(5);
		char c1, c2;
		int i = rnd.nextInt(word.length());
		c1 = word.charAt(i);
		while(puzzle[i] != HIDDEN_CHAR)
		{
			i = rnd.nextInt(word.length());
			c1 = word.charAt(i);
		}
		
		i = rnd.nextInt(25);
		c2 = (char)(i+97);
		while(already_guessed[i] || word.indexOf(c2) != -1)
		{
			i = rnd.nextInt(25);
			c2 = (char)(i+97);
		}
		char [] hints = {c1,c2};
		Arrays.sort(hints);
		return hints;
	}
	
	
	public static char[] mainTemplateSettings(String word, Scanner inputScanner) { // Q - 6
		printSettingsMessage();
		boolean proceed = true;
		char [] puzzle = new char[word.length()];
		while(proceed)
		{
			printSelectTemplate();
			boolean [][] templates;
			//inputScanner = new Scanner(System.in);
			int choise = inputScanner.nextInt(), numOfCharecters;
			
			if(choise == 1)
			{
				printSelectNumberOfHiddenChars();
				//inputScanner = new Scanner(System.in);
				numOfCharecters = inputScanner.nextInt();
				templates = getAllLegalTemplates(word, numOfCharecters);
				if(templates.length == 0)
					printWrongTemplateParameters();
				else
				{
					Random rnd = new Random();
					int row = rnd.nextInt(templates.length);
					puzzle = createPuzzleFromTemplate(word, templates[row]);
					proceed = false;
				}
			}
			else
			{
				printEnterPuzzleTemplate();
				int i = 0;
				String temp = inputScanner.next();
				String [] arr = temp.split(",");
				while(i < arr.length && i < word.length())
				{
					puzzle[i] = arr[i].charAt(0);
					i++;
				}
				boolean[] template = new boolean[word.length()];
				for (int j = 0; j < puzzle.length; j++) {
					if(puzzle[j] == HIDDEN_CHAR)
						template[j] = true;
				}
				if(!checkLegalTemplate(word, template))
					printWrongTemplateParameters();
				else
					proceed = false;
			}
		}
		return puzzle;
	}
	
	public static void mainGame(String word, char[] puzzle, Scanner inputScanner){ // Q - 7
		printGameStageMessage();
		char guess;
		int tries = numOfHides(puzzle)+3, index;
		int finish = numOfHides(puzzle);
		boolean [] already_guessed = new boolean[26];
		char [] hints = new char[2];
		
		while(tries > 0)
		{
			for (int i = 0; i < puzzle.length; i++) {
				System.out.print(puzzle[i]);
			}
			System.out.println();
			printEnterYourGuessMessage();
			guess = inputScanner.next().charAt(0);
			if(guess == 'H')
			{
				hints = getHint(word, puzzle, already_guessed);
				printHint(hints);
			}
			else
			{
				index = guess - 97;
				already_guessed[index] = true;
				int dec = applyGuess(guess, word, puzzle); 
				finish -= dec;
				if(finish == 0) 
				{
					printWinMessage();
					break;
				}
				else if(dec > 0)
				{
					tries--;
					printCorrectGuess(tries);
				}
				else
				{
					tries--;
					printWrongGuess(tries);
				}
			}
		}
		if(tries == 0)
		{
			printGameOver();
		}
	}
	private static int numOfHides(char [] puzzle)
	{
		int count = 0;
		for (int i = 0; i < puzzle.length; i++) {
			if(puzzle[i] == HIDDEN_CHAR)
				count++;
		}
		return count;
	}
/*************************************************************/
/********************* Don't change this ********************/
/*************************************************************/

	public static void main(String[] args) throws Exception { 
		if (args.length < 1){
			throw new Exception("You must specify one argument to this program");
		}
		String wordForPuzzle = args[0].toLowerCase();
		if (wordForPuzzle.length() > 10){
			throw new Exception("The word should not contain more than 10 characters");
		}
		Scanner inputScanner = new Scanner(System.in);
		char[] puzzle = mainTemplateSettings(wordForPuzzle, inputScanner);
		mainGame(wordForPuzzle, puzzle, inputScanner);
		inputScanner.close();
	}


	public static void printSettingsMessage() {
		System.out.println("--- Settings stage ---");
	}

	public static void printSelectNumberOfHiddenChars(){
		System.out.println("Enter number of hidden characters:");
	}
	public static void printSelectTemplate() {
		System.out.println("Choose a (1) random or (2) manual template:");
	}
	
	public static void printWrongTemplateParameters() {
		System.out.println("Cannot generate puzzle, try again.");
	}
	
	public static void printEnterPuzzleTemplate() {
		System.out.println("Enter your puzzle template:");
	}


	public static void printPuzzle(char[] puzzle) {
		System.out.println(puzzle);
	}


	public static void printGameStageMessage() {
		System.out.println("--- Game stage ---");
	}

	public static void printEnterYourGuessMessage() {
		System.out.println("Enter your guess:");
	}

	public static void printHint(char[] hist){
		System.out.println(String.format("Here's a hint for you: choose either %s or %s.", hist[0] ,hist[1]));

	}
	public static void printCorrectGuess(int attemptsNum) {
		System.out.println("Correct Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWrongGuess(int attemptsNum) {
		System.out.println("Wrong Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWinMessage() {
		System.out.println("Congratulations! You solved the puzzle!");
	}

	public static void printGameOver() {
		System.out.println("Game over!");
	}
}