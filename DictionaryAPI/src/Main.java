import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws Exception {
		
		printResults(API.Search("en", "ko", "taste"));
		printResults(API.Search("en", "ko", "word"));
		printResults(API.Search("en", "ko", "awesome"));
		printResults(API.Search("en", "ko", "let's go"));
		printResults(API.Search("en", "ko", "apartment"));
		printResults(API.Search("en", "ko", "considering that"));
		printResults(API.Search("en", "ko", "social"));
		printResults(API.Search("en", "ko", "rib"));
		printResults(API.Search("en", "ko", "in the first place"));
		printResults(API.Search("en", "ko", "region"));
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
