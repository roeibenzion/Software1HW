package il.ac.tau.cs.sw1.ex8.wordsRank;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;
import il.ac.tau.cs.sw1.ex8.histogram.IHistogram;
import il.ac.tau.cs.sw1.ex8.wordsRank.RankedWord.rankType;
import il.ac.tau.cs.sw1.ex8.histogram.*;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class FileIndex {
	
	public static final int UNRANKED_CONST = 30;
	
	
	HashMap<String, HashMapHistogram<String>> index;
	HashMap<String, HashMap<String, Integer>> rankedWords;
	/*
	 * @pre: the directory is no empty, and contains only readable text files
	 */
  	public void indexDirectory(String folderPath) {
		//This code iterates over all the files in the folder. add your code wherever is needed
  		if(index == null)
  			index = new HashMap<String, HashMapHistogram<String>>();
  		if(rankedWords == null)
  			rankedWords = new HashMap<String, HashMap<String,Integer>>();
  		
		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		for (File file : listFiles) {
			// for every file in the folder
			HashMapHistogram<String> hist = new HashMapHistogram<String>();
			if (file.isFile()) {
				try {
					List<String> lst = FileUtils.readAllTokens(file);
					Iterator<String> itr = lst.iterator();
					while(itr.hasNext())
					{
						String item = itr.next();
						hist.addItem(item);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			index.put(file.getName(), hist);
		}
		Set<String> temp, st = new HashSet<String>();
		for (String s : index.keySet()) {
			temp = index.get(s).getItemsSet();
			for (String word : temp) {
				if(!st.contains(word))
				{
					st.add(word);
				}
			}
		}
		for (String word : st) {
			HashMap<String, Integer> rankFile = new HashMap<String, Integer>();
			for (String file : index.keySet()) {
				try {
					rankFile.put(file, getRankForWordInFile(file, word));
				} catch (FileIndexException e) {
					e.printStackTrace();
				}
			}
			rankedWords.put(word, rankFile);
		}
	}
	
  	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getCountInFile(String filename, String word) throws FileIndexException{
		try {
			int num = index.get(filename).getCountForItem(word.toLowerCase());
			return num;
		} catch (Exception e) {
			throw new FileIndexException("File not in index");
		}
	}
	
	/*
	 * @pre: the index is initialized
	 * @pre filename is a name of a valid file
	 * @pre word is not null
	 */
	public int getRankForWordInFile(String filename, String word) throws FileIndexException{
		int rank = 1;
		try {
			HashMapHistogram<String> hist = index.get(filename);
			Iterator<String> itr = hist.iterator();
			while(itr.hasNext())
			{
				String s = itr.next();
				if(s.equals(word.toLowerCase()))
					return rank;
				rank++;
			}
		} catch (Exception e) {
			throw new FileIndexException("file not found in index");
		}
		return rank + UNRANKED_CONST - 1; //replace this with the actual returned value

	}
	
	/*
	 * @pre: the index is initialized
	 * @pre word is not null
	 */
	public int getAverageRankForWord(String word){
		HashMap<String, Integer> ranked = rankedWords.get(word);
		int ave = 0;
		if(ranked != null) {
			RankedWord rnk = new RankedWord(word, ranked);
			ave = rnk.getRankByType(rankType.average);
		}
		if(ave == 0)
		{
			for (String name : index.keySet()) {
				ave += this.index.get(name).getItemsSet().size() + UNRANKED_CONST;
			}
			ave /= index.keySet().size();
		}
		return ave;
	}
	private int getRankByType(String word, rankType type)
	{
		RankedWord rnk = new RankedWord(word, rankedWords.get(word));
		if(type == rankType.average)
			return getAverageRankForWord(word);
		return rnk.getRankByType(type);
	}
	
	private List<String> getWordsWithTypeSmallerThenK(int k, rankType type)
	{
		List<RankedWord> ranked = new ArrayList<RankedWord>();
		for (String word : rankedWords.keySet()) {
			RankedWord tmp = new RankedWord(word, rankedWords.get(word));
			if(tmp.getRankByType(type) < k)
			{
				ranked.add(tmp);
			}
		}
		RankedWordComparator c = new RankedWordComparator(type);
		Collections.sort(ranked, c);
		List<String> words = new ArrayList<String>();
		for (RankedWord rankedWord : ranked) {
			words.add(rankedWord.getWord());
		}
		return words;
	}
	public List<String> getWordsWithAverageRankSmallerThanK(int k){
		return getWordsWithTypeSmallerThenK(k, rankType.average); //replace this with the actual returned value
	}
	
	public List<String> getWordsWithMinRankSmallerThanK(int k){
		return getWordsWithTypeSmallerThenK(k, rankType.min); //replace this with the actual returned value
	}
	
	public List<String> getWordsWithMaxRankSmallerThanK(int k){
		return getWordsWithTypeSmallerThenK(k, rankType.max ); //replace this with the actual returned value
	}
}
