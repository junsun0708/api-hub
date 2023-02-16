package com.example.apihub.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class HttpUtil {
	public static String callApi(String callType, String callUrl, Map<String, String> params) {

		HttpURLConnection conn = null;
		// JsonObject responseJson = null;
		String result = "";

		try {
			URL url = new URL(callUrl);
			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod(callType);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Transfer-Encoding", "chunked");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setDoOutput(true);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String jsonOutput = gson.toJson(params);

			bw.write(jsonOutput);
			bw.flush();
			bw.close();

			//int responseCode = conn.getResponseCode();
			// if (responseCode == 200) {
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			// responseJson = new JsonObject().getAsJsonObject(sb.toString());

			// 응답 데이터
			// System.out.println("responseJson :: " + responseJson);
			return sb.toString();
			// }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			System.out.println("not JSON Format response");
			e.printStackTrace();
		}
		return result;
	}

	public static void loop(Map<String, String> map) {
		System.out.println("=== input값 ===");
		for (String key : map.keySet()) {
			System.out.println(key + " , " + map.get(key));
		}
		// keyset보다 속력이 느려 람다는 사용 안함.
		// map.forEach((key, value) -> System.out.println(key + " , " + map.get(key)));
	}

}