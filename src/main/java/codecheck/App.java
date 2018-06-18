package codecheck;

import sun.net.www.protocol.http.HttpURLConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class App {
	public static void main(String[] args) {
		for (int i = 0, l = args.length; i < l; i++) {
			String output = String.format("argv[%s]: %s", i, args[i]);
			System.out.println(output);
			call(args[i]);
		}
	}

	private static void call(String q) {
		try {
			String sUrl = "http://challenge-server.code-check.io/api/hash?q=";
			URL url = new URL(sUrl + q);
			System.out.println("1");

			HttpURLConnection connection = null;
			System.out.println("2");

			try {
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				System.out.println("3");
				System.out.println(q);

				connection.connect();
				System.out.println("4");
				System.out.println(connection.getResponseCode());


				//ここでレスポンスのボディからhashの値を取りたい
				InputStream is = connection.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
				StringBuilder sbBody = new StringBuilder();
				String s;
				while ((s = reader.readLine()) != null) {
					sbBody.append(s);
				}

                Map<String,String> map = new LinkedHashMap<>();
                ObjectMapper mapper = new ObjectMapper();
				try {
					map = mapper.readValue(sbBody.toString(), new TypeReference<LinkedHashMap<String,String>>(){});
				} catch (Exception e) {
					e.printStackTrace();
				}

				System.out.println(map.get("hash"));








				//System.out.println(sbBody.toString());

				/*
				// ヘッダーの取り方だった
				LinkedHashMap<String, String> resHeader = new LinkedHashMap<String, String>();
				Map<String, List<String>> header = connection.getHeaderFields();
				Iterator<String> headerIt = header.keySet().iterator();

				while (headerIt.hasNext()) {
					String key = headerIt.next();
					List<String> valList = header.get(key);
					if (key != null) {
						StringBuilder sb = new StringBuilder();
						for (String val : valList) {
							if (sb.length() > 0)
								sb.append("\n");
							sb.append(val);
						}
						resHeader.put(key, sb.toString());
					}
				}
				*/



				//InputStream is = connection.getInputStream();
				//BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));

				//String hash = getResponse(connection);
				//System.out.println(hash);

			} finally {
				if (connection != null) {
					connection.disconnect();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

/*	private  static  String getResponse(HttpURLConnection connection) {

	//				if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					//Map headers = connection.getHeaderFields();
				   //String hash = headers.get("hash").toString();
				InputStream is = connection.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
				StringBuffer sbBody = new StringBuffer();
				String s;
				while ((s = reader.readLine()) != null) {
					sbBody.append(s);
					sbBody.append("\n");
				}


//				}

	}*/

}
