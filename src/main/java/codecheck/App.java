package codecheck;

import sun.net.www.protocol.http.HttpURLConnection;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class App {
	public static void main(String[] args) {
		for (int i = 0, l = args.length; i < l; i++) {
			String output = String.format("argv[%s]: %s", i, args[i]);
			System.out.println(output);
			call(args[i]);
		}
	}

	public static void call(String q){
		try{
			URL url = new URL( "http://challenge-server.code-check.io/api/hash");
			System.out.println("1");

			HttpURLConnection connection = null;
			System.out.println("2");

			try {
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("q", q);
				connection.setRequestProperty("hash",null);
				System.out.println("3");

				connection.connect();
				System.out.println("4");
				System.out.println(connection.getResponseCode());

//				if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					Map headers = connection.getHeaderFields();
					String hash = headers.get("hash").toString();
					System.out.println(hash);
//				}
			} finally {
				if (connection != null) {
					connection.disconnect();;
				}
			}
		} catch (IOException e) {
				e.printStackTrace();;
		}
	}
}
