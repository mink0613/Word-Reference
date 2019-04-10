import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class API {

	private static final int SEARCH_MAX = 3;
	
	private static final String BASE_URL = "http://www.wordreference.com/";
	
	public static ArrayList<Word> Search(String langFrom, String langTo, String searchWord) throws Exception {
		
		ArrayList<Word> meanings = new ArrayList<>();
		
		String url = BASE_URL + langFrom + langTo + "/" + searchWord;
		
		Document doc = Jsoup.connect(url).timeout(60 * 1000).get(); // 5 seconds of timeout
		Element result = doc.getElementsByClass("WRD").first();
		if (result == null) {
			
			return meanings;
		}

		// Try to get pronunciation of searching word
		Element pronun = doc.getElementById("pronWR");
		String pronunStr = "";
		if (pronun != null) {
			
			pronunStr = pronun.text();
		}
		
		Elements items = result.select("tr");
		
		int foundResult = 0;
		
		for (int i = 0; i < items.size(); i++) {
		
			Element wordTR = items.get(i);
			if (!wordTR.className().equals("odd") && !wordTR.className().equals("even")) {
				
				continue;
			}
			
			Element fromWord = wordTR.getElementsByClass("FrWrd").first();
			if (fromWord == null) {
				
				continue;
			}
			
			// If the from word does not exactly match, then skip
			String word = fromWord.text().trim();
			//if (!word.equals(searchWord)) {
			if (!isContain(word, searchWord)) {
				
				continue;
			}
			
			Element toWord = wordTR.getElementsByClass("ToWrd").first();
			if (toWord == null) {
				
				continue;
			}
			
			String strResult = "";
			Word meaning = new Word();
			meanings.add(meaning);
			
			meaning.setLangFrom(langFrom);
			meaning.setLangTo(langTo);
			meaning.setPronunciation(pronunStr);
			
			int tempI = i;
			do {
				
				strResult += toWord.ownText() + ", ";
				tempI++;
				if (tempI >= items.size()) {
					
					break;
				}
				
				toWord = items.get(tempI).getElementsByClass("ToWrd").first();
			} while (toWord != null);
			
			// Remove ending ", "
			strResult = strResult.substring(0, strResult.length() - 2);
			
			meaning.setWordFrom(searchWord);
			meaning.setWordTo(strResult);
			
			i = tempI;
			
			if (i >= items.size()) {
				
				break;
			}
			
			//i++; 
			// Next tr contains example in eng
			Element fromExTR = items.get(i);
			Element frex = fromExTR.getElementsByClass("FrEx").first();
			if (frex != null) {
				
				meaning.setExampleFrom(frex.text().trim());
			}
			
			i++; // Next tr contains example in kor
			
			if (i >= items.size()) {
				
				break;
			}

			Element toExTR = items.get(i);
			Element toex = toExTR.getElementsByClass("ToEx").first();
			if (toex != null) {
				
				meaning.setExampleTo(toex.text().trim());
			}
			
			//meanings.add(meaning);
			foundResult++;
			
			if (foundResult == SEARCH_MAX) {
				
				break;
			}
		}
		
		return meanings;
	}
	
	public static ArrayList<Word> SearchFull(String langFrom, String langTo, String searchWord) throws Exception {
		
		ArrayList<Word> meanings = new ArrayList<>();
		
		String url = BASE_URL + langFrom + langTo + "/" + searchWord;
		
		Document doc = Jsoup.connect(url).timeout(60 * 1000).get(); // 5 seconds of timeout
		
		Elements results = doc.getElementsByClass("WRD");
		if (results == null) {
			
			return meanings;
		}
		
		// Try to get pronunciation of searching word
		Element pronun = doc.getElementById("pronWR");
		String pronunStr = "";
		if (pronun != null) {
			
			pronunStr = pronun.text();
		}
		
		for (int index = 0; index < results.size(); index++) {
			
			Element result = results.get(index);
			if (result == null) {
				
				return meanings;
			}
			
			Elements items = result.select("tr");
			
			int foundResult = 0;
			
			for (int i = 0; i < items.size(); i++) {
			
				Element wordTR = items.get(i);
				if (!wordTR.className().equals("odd") && !wordTR.className().equals("even")) {
					
					continue;
				}
				
				Element fromWord = wordTR.getElementsByClass("FrWrd").first();
				if (fromWord == null) {
					
					continue;
				}
				
				// If the from word does not exactly match, then skip
				String word = fromWord.text().trim();
				//if (!word.equals(searchWord)) {
				if (!isContain(word, searchWord)) {
					
					continue;
				}
				
				Element toWord = wordTR.getElementsByClass("ToWrd").first();
				if (toWord == null) {
					
					continue;
				}
				
				String strResult = "";
				Word meaning = new Word();
				meanings.add(meaning);
				
				meaning.setLangFrom(langFrom);
				meaning.setLangTo(langTo);
				meaning.setPronunciation(pronunStr);
				
				int tempI = i;
				do {
					
					strResult += toWord.ownText() + ", ";
					tempI++;
					if (tempI >= items.size()) {
						
						break;
					}
					
					toWord = items.get(tempI).getElementsByClass("ToWrd").first();
				} while (toWord != null);
				
				// Remove ending ", "
				strResult = strResult.substring(0, strResult.length() - 2);
				
				meaning.setWordFrom(searchWord);
				meaning.setWordTo(strResult);
				
				i = tempI;
				
				if (i >= items.size()) {
					
					break;
				}
				
				//i++; 
				// Next tr contains example in eng
				Element fromExTR = items.get(i);
				Element frex = fromExTR.getElementsByClass("FrEx").first();
				if (frex != null) {
					
					meaning.setExampleFrom(frex.text().trim());
				}
				
				i++; // Next tr contains example in kor
				
				if (i >= items.size()) {
					
					break;
				}

				Element toExTR = items.get(i);
				Element toex = toExTR.getElementsByClass("ToEx").first();
				if (toex != null) {
					
					meaning.setExampleTo(toex.text().trim());
				}
				
				//meanings.add(meaning);
				foundResult++;
				
				if (foundResult == SEARCH_MAX) {
					
					break;
				}
			}
		}
		
		return meanings;
	}
	
	private static boolean isContain(String source, String subItem){
		
        String patternString = "\\b"+subItem+"\\b";
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(source);
        return matcher.find();
   }
}
