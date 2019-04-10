import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws Exception {
		
		//readWordFile();
		testToeicWords();
		/*String test = "아dd zz하하하korean좋11아!!!";
		System.out.println(test.replaceAll("[^a-zA-Z ]+", ""));*/
		
		//printResults(API.SearchFull("en", "ko", "moslem"));
		/*printResults(API.Search("en", "ko", "taste"));
		printResults(API.Search("en", "ko", "word"));
		printResults(API.Search("en", "ko", "awesome"));
		printResults(API.Search("en", "ko", "let's go"));
		printResults(API.Search("en", "ko", "apartment"));
		printResults(API.Search("en", "ko", "considering that"));
		printResults(API.Search("en", "ko", "social"));
		printResults(API.Search("en", "ko", "rib"));
		printResults(API.Search("en", "ko", "in the first place"));
		printResults(API.Search("en", "ko", "region"));*/
	}
	
	private static void testToeicWords() throws Exception {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("toeic_words_founds_only.txt"));
		BufferedReader reader = new BufferedReader(new FileReader("toeic_words.txt"));
		String word;
		
		ArrayList<String> notFoundList = new ArrayList<>();
		ArrayList<String> foundList = new ArrayList<>();
		
		while ((word = reader.readLine()) != null) {
			
			System.out.print("Searching for... :\t\t" + word);
			ArrayList<Word> meanings = API.SearchFull("en", "ko", word);
			if (meanings.size() == 0) {
				
				System.out.println("\t\tis not found.");
				notFoundList.add(word);
			} else {
				
				System.out.println("\t\tis successfully found. Meanings: " + meanings.size());
				if (foundList.contains(word) == false) {
					
					foundList.add(word);
				}
			}
		}
		
		reader.close();
		
		for (int i = 0; i < foundList.size(); i++) {
			
			writer.write(foundList.get(i) + "\r\n");
			writer.flush();
		}

		writer.close();
		
		System.out.println("***********************************************************************");
		System.out.println("Not Found Word List");
		System.out.println("***********************************************************************\r\n");
		for (int i = 0; i < notFoundList.size(); i++) {
			
			System.out.println(notFoundList.get(i));
		}
	}
	
	private static void readWordFile() throws Exception {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("toeic_words.txt"));
		BufferedReader reader = new BufferedReader(new FileReader("Files\\toeic_rough.txt"));
		String line;
		while ((line = reader.readLine()) != null) {
			
			String trimmed = line.replaceAll("[^a-zA-Z ]+", "").trim();
			writer.write(trimmed);
			writer.write("\r\n");
			writer.flush();
		}
		
		writer.close();
		reader.close();
	}
	
	private static void printResults(ArrayList<Word> meanings) {
		
		System.out.println("*************************************************************************************");
		for (int i = 0; i < meanings.size(); i++) {
			
			Word meaning = meanings.get(i);
			
			String strResult = "";
			
			strResult += "From: " + meaning.getWordFrom() + "\t";
			strResult += "To: " + meaning.getWordTo() + "\n";
			strResult += "Example From: " + meaning.getExampleFrom() + "\t";
			strResult += "Example To: " + meaning.getExampleTo();
			
			System.out.println(strResult);
		}
		System.out.println("*************************************************************************************\n");
	}
}
