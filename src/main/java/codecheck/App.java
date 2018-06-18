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
			call(args[i]);
		}
	}

	private static void call(String q) {
		try {
			String sUrl = "http://challenge-server.code-check.io/api/hash?q=";
			URL url = new URL(sUrl + q + "\"");
			HttpURLConnection connection = null;

			try {
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();

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

			} finally {
				if (connection != null) {
					connection.disconnect();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
