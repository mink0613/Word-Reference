import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	public static void main(String[] args) throws Exception {
		
		Document doc = Jsoup.connect("http://www.wordreference.com/enko/taste").get();
		Element result = doc.getElementsByClass("WRD").first();
		Elements items = result.select("tr");
		
		int upToResult = 3;
		int foundResult = 0;
		
		for (int i = 0; i < items.size(); i++) {
			
			Element wordTR = items.get(i);
			if (!wordTR.className().equals("odd") && !wordTR.className().equals("even")) {
				
				System.out.println(wordTR.className() + " is skipped");
				continue;
			}
			
			String strResult = "";
			
			Element word = wordTR.getElementsByClass("ToWrd").first();
			if (word == null) {
				
				continue;
			}
			
			int tempI = i;
			strResult += "Korean: ";
			do {
				
				strResult += word.ownText() + ", ";
				tempI++;
				word = items.get(tempI).getElementsByClass("ToWrd").first();
			} while (word != null);
			
			strResult = strResult.substring(0, strResult.length() - 2);
			strResult += "\t";
			i = tempI;
			
			//i++; 
			// Next tr contains example in eng
			Element fromExTR = items.get(i);
			Element frex = fromExTR.getElementsByClass("FrEx").first();
			if (frex == null) {
				
				continue;
			}
			strResult += "From Ex: " + frex.text() + "\t";
			
			i++; // Next tr contains example in kor
			Element toExTR = items.get(i);
			Element toex = toExTR.getElementsByClass("ToEx").first();
			if (toex == null) {
				
				continue;
			}
			strResult += "To Ex: " + toex.text() + "\t";
			
			System.out.println(strResult);
			
			foundResult++;
			
			if (foundResult == upToResult) {
				
				System.out.println("Stopped");
				break;
			} else {
				
				System.out.println("Found result: " + foundResult);
			}
		}
		/*String result = Http.Get("http://www.wordreference.com/enko/taste");
		System.out.println(result);*/
	}
}
