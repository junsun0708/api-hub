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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class HttpUtil {
	public static void callApi(String type, String url1, JsonObject params) {

		HttpURLConnection conn = null;
		JsonObject responseJson = null;

		try {
			// URL 설정
			URL url = new URL(url1);

			conn = (HttpURLConnection) url.openConnection();

			// type의 경우 POST, GET, PUT, DELETE 가능
			conn.setRequestMethod(type);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Transfer-Encoding", "chunked");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setDoOutput(true);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			// JSON 형식의 데이터 셋팅
			JsonObject commands = new JsonObject();
			JsonArray jsonArray = new JsonArray();

//			params.addProperty("key", 1);
//			params.addProperty("age", 20);
//			params.addProperty("userNm", "홍길동");
//
//			commands.add("userInfo", params);
			// JSON 형식의 데이터 셋팅 끝

			// 데이터를 STRING으로 변경
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			//String jsonOutput = gson.toJson(commands);
			String jsonOutput = gson.toJson(params);

			//bw.write(commands.toString());
			bw.write(params.toString());
			bw.flush();
			bw.close();

			// 보내고 결과값 받기
			int responseCode = conn.getResponseCode();
			//if (responseCode == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				responseJson = new JsonObject().getAsJsonObject(sb.toString());

				// 응답 데이터
				System.out.println("responseJson :: " + responseJson);
			//}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			System.out.println("not JSON Format response");
			e.printStackTrace();
		}
	}
	
	public static void loop(Map<String, String> map) {
		for (String key : map.keySet()) {
			System.out.println(key + " , " + map.get(key));
		}
		// keyset보다 속력이 느려 람다는 사용 안함.
		// map.forEach((key, value) -> System.out.println(key + " , " + map.get(key)));
	}
}