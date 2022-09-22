package il.ac.tau.cs.sw1.ex5;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class BigramModel {


	public static final int MAX_VOCABULARY_SIZE = 14500;
	public static final String VOC_FILE_SUFFIX = ".voc";
	public static final String COUNTS_FILE_SUFFIX = ".counts";
	public static final String SOME_NUM = "some_num";
	public static final int ELEMENT_NOT_FOUND = -1;
	
	String[] mVocabulary;
	int[][] mBigramCounts;
	
	// DO NOT CHANGE THIS !!! 
	public void initModel(String fileName) throws IOException{
		mVocabulary = buildVocabularyIndex(fileName);
		mBigramCounts = buildCountsArray(fileName, mVocabulary);
		
	}
	
	
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public String[] buildVocabularyIndex(String fileName) throws IOException{ // Q 1
		// replace with your code
		File fromFile = new File(fileName);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fromFile));
		String [] temp = new String [MAX_VOCABULARY_SIZE];
		String line;
		int wordNum = 0;
		boolean validFirst, validSecond, numLocation = false;
		while((line = bufferedReader.readLine()) != null && wordNum < MAX_VOCABULARY_SIZE)
		{
			String [] sentence = line.split(" ");
			for (int i = 0; i < sentence.length; i++) 
			{
				if(sentence[i].equals(""))
					continue;
				validFirst = isValidFirst(sentence[i]);
				validSecond = isValidSecond(sentence[i]);
				
				if(validFirst && !isExists(temp, sentence[i].toLowerCase()))
				{
					temp[wordNum++] = sentence[i].toLowerCase();
				}
				if(validSecond && !numLocation)
				{
					temp[wordNum++] = SOME_NUM;
					numLocation = true;
				}
			}
		}
		String[] words = new String[wordNum];
		for (int i = 0; i < words.length; i++) {
			words[i] = temp[i];
		}
		bufferedReader.close();
		return words;
	}
	private static boolean isValidFirst(String word)
	{
		for (int i = 0; i < word.length(); i++) 
		{
			if(('a' <= word.charAt(i) && word.charAt(i) <= 'z')
					||('A' <= word.charAt(i) && word.charAt(i) <= 'Z'))
				return true;
		}
		return false;
	}
	
	private static boolean isValidSecond(String word)
	{
		for (int i = 0; i < word.length(); i++) {
			if(!Character.isDigit(word.charAt(i)))
				return false;
		}
		return true;
	}
	
	private static boolean isExists(String [] words, String word)
	{
		for (int i = 0; i < words.length; i++) {
			if(word.equals(words[i]))
				return true;
		}
		return false;
	}
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public int[][] buildCountsArray(String fileName, String[] vocabulary) throws IOException{ // Q - 2
		// replace with your code
		int [][] mat = new int [vocabulary.length][vocabulary.length];
		File fromFile = new File(fileName);
		String line;
		int numLocation = 0;
		for (int i = 0; i < vocabulary.length; i++) {
			if(vocabulary[i] == SOME_NUM)
			{
				numLocation = i;
				break;
			}
		}
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fromFile));
		while((line = bufferedReader.readLine()) != null)
		{
			String[] sentence = line.split(" ");
			for (int i = 0; i < sentence.length-1; i++) {
				
				int indexI = isInVocAndLegal(sentence[i].toLowerCase(), vocabulary), 
						indexII = isInVocAndLegal(sentence[i+1].toLowerCase(), vocabulary);
				
				if(indexI != -1 && indexII != -1)
					mat[indexI][indexII]++;
				else if(indexI != -1 && isValidSecond(sentence[i+1]))
					mat[indexI][numLocation]++;
				else if(indexII != -1 && isValidSecond(sentence[i]))
					mat[numLocation][indexII]++;
				else if(isValidSecond(sentence[i]) && isValidSecond(sentence[i+1]))
					mat[numLocation][numLocation]++;
			}
		}
		bufferedReader.close();
		return mat;
	}
	private static int isInVocAndLegal(String word, String[] voc)
	{
		for (int i = 0; i < voc.length; i++) {
			if(voc[i].equals(word) && isValidFirst(word))
				return i;
		}
		return -1;
	}
	
	
	/*
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: fileName is a legal file path
	 */
	public void saveModel(String fileName) throws IOException{ // Q-3
		// add your code here
		File toFileVoc = new File(fileName + VOC_FILE_SUFFIX);
		File toFileCount = new File(fileName + COUNTS_FILE_SUFFIX);
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(toFileVoc));
		bufferedWriter.write(mVocabulary.length + " words");
		bufferedWriter.newLine();
 		for (int i = 0; i < mVocabulary.length; i++) {
			bufferedWriter.write(i + "," + mVocabulary[i]);
			bufferedWriter.newLine();
		}
 		bufferedWriter.close();
 		bufferedWriter = new BufferedWriter(new FileWriter(toFileCount));
		for (int i = 0; i < mBigramCounts.length; i++) {
			for (int j = 0; j < mBigramCounts[i].length; j++) {
				if(mBigramCounts[i][j] > 0)
				{
					bufferedWriter.write(i + "," + j + ":" + mBigramCounts[i][j]);
					bufferedWriter.newLine();
				}
			}
		}
		bufferedWriter.close();
	}
	/*
	 * @pre: fileName is a legal file path
	 */
	public void loadModel(String fileName) throws IOException{ // Q - 4
		// add your code here
		File fromFileVoc = new File(fileName + VOC_FILE_SUFFIX);
		File fromFileCount = new File(fileName + COUNTS_FILE_SUFFIX);
		String [] words = new String [MAX_VOCABULARY_SIZE];
		int [][] count = new int [MAX_VOCABULARY_SIZE][MAX_VOCABULARY_SIZE];
		
		BufferedReader bufferedReaderVoc = new BufferedReader(new FileReader(fromFileVoc)),
				bufferedReaderCount = new BufferedReader(new FileReader(fromFileCount));
		String line;
		int wordNum = 0;
		bufferedReaderVoc.readLine();
		while((line = bufferedReaderVoc.readLine()) != null && wordNum < words.length)
		{
			words[wordNum] = line.split(",")[1];
			wordNum++;
		}
		
		while((line = bufferedReaderCount.readLine()) != null)
		{
			String[] num = line.split(":");
			String [] app = num[0].split(",");
			count[Integer.parseInt(app[0])][Integer.parseInt(app[1])] = Integer.parseInt(num[1]);
		}
		mVocabulary = new String [wordNum];
		mBigramCounts = new int [wordNum][wordNum];
		mVocabulary = Arrays.copyOf(words, mVocabulary.length);
		for (int i = 0; i < wordNum; i++) {
			mBigramCounts[i] = Arrays.copyOf(count[i], wordNum);
		}
		bufferedReaderCount.close();
		bufferedReaderVoc.close();
	}
	/*
	 * @pre: word is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: word is in lowercase
	 * @post: $ret = -1 if word is not in vocabulary, otherwise $ret = the index of word in vocabulary
	 */
	public int getWordIndex(String word){  // Q - 5
		// replace with your code
		for (int i = 0; i < mVocabulary.length; i++) {
			if(mVocabulary[i].equals(word))
				return i;
		}
		return ELEMENT_NOT_FOUND;
	}
	
	
	
	/*
	 * @pre: word1, word2 are in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = the count for the bigram <word1, word2>. if one of the words does not
	 * exist in the vocabulary, $ret = 0
	 */
	public int getBigramCount(String word1, String word2){ //  Q - 6
		// replace with your code
		int i = getWordIndex(word1), j = getWordIndex(word2);
		
		if(i < 0 || j < 0)
			return 0;
		
		return mBigramCounts[i][j];
	}
	
	
	/*
	 * @pre word in lowercase, and is in mVocabulary
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post $ret = the word with the lowest vocabulary index that appears most fequently after word (if a bigram starting with
	 * word was never seen, $ret will be null
	 */
	public String getMostFrequentProceeding(String word){ //  Q - 7
		// replace with your code
		String maxWord = mVocabulary[0];
		int max = 0, i = getWordIndex(word);
		for (int j = 0; j < mBigramCounts.length; j++) {
			if(mBigramCounts[i][j] > max)
			{
				max = mBigramCounts[i][j];
				maxWord = mVocabulary[j];
			}
		}
		if(max == 0)
			return null;
		return maxWord;
	}
	
	
	/* @pre: sentence is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: each two words in the sentence are are separated with a single space
	 * @post: if sentence is is probable, according to the model, $ret = true, else, $ret = false
	 */
	public boolean isLegalSentence(String sentence){  //  Q - 8
		// replace with your code
		if(sentence.equals(""))
			return true;
		String [] wordsInSentence = sentence.split(" ");
		int i = getWordIndex(wordsInSentence[0]);
		for (int k = 1; k < wordsInSentence.length; k++) {
			int j = getWordIndex(wordsInSentence[k]);
			if(i == -1 || j == -1 || mBigramCounts[i][j] == 0)
				return false;
			i = j;
		}
		return true && i != -1;
	}
	
	/*
	 * @pre: arr1.length = arr2.legnth
	 * post if arr1 or arr2 are only filled with zeros, $ret = -1, otherwise calcluates CosineSim
	 */
	public static double calcCosineSim(int[] arr1, int[] arr2){ //  Q - 9
		// replace with your code
		double sum1=0, sum2=0, sum3=0;
		for (int i = 0; i < arr1.length; i++) {
			sum1 += (double)(arr1[i]*arr2[i]);
		}
		for (int i = 0; i < arr1.length; i++) {
			sum2 += (double)(arr1[i]*arr1[i]);
		}
		sum2 = Math.sqrt(sum2);
		
		for (int i = 0; i < arr2.length; i++) {
			sum3 += (double)(arr2[i]*arr2[i]);
		}
		sum3 = Math.sqrt(sum3);
		
		if(sum2 == 0 || sum3 == 0)
			return -1;
		
		return sum1 / (sum2 * sum3);
	}

	
	/*
	 * @pre: word is in vocabulary
	 * @pre: the method initModel was called (the language model is initialized), 
	 * @post: $ret = w implies that w is the word with the largest cosineSimilarity(vector for word, vector for w) among all the
	 * other words in vocabulary
	 */
	public String getClosestWord(String word){ //  Q - 10
		// replace with your code
		int i = getWordIndex(word);
		int [] arr = mBigramCounts[i];
		String w = mVocabulary[0];
		double max = -2;
		for (int j = 0; j < mBigramCounts.length; j++) 
		{
			if(j == i)
				continue;
			else 
			{
				double valJ = calcCosineSim(arr, mBigramCounts[j]); 
				if(max < valJ)
				{
					max = valJ;
					w = mVocabulary[j];
				}
			}
		}
		return w;
	}
	/*
	 * @pre: word is a String
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = the number of word's occurrences in the text.
	 */
	public int getWordCount(String word){ //  Q - 11
		// replace with your code
		return 0;
	}
}