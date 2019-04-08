import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Http {

	public static String Get(String strUrl) throws Exception {
		
		URL url = new URL(strUrl);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

		con.setRequestMethod("GET");
		con.setDoOutput(true);

		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			
			response.append(inputLine);
		}
		
		in.close();
		
		return response.toString();
	}
}
