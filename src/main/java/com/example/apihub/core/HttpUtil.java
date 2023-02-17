package com.example.apihub.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import com.example.apihub.base.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class HttpUtil {
	
	
	public static void callApi2(Map<String, String> params) throws Exception{
        // Sending get request
        URL url = new URL("http://localhost:8080/api/feEngBas/list");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestProperty("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJWVVAiLCJhdXRoIjoiUk9MRV9BRE1JTiIsImV4cCI6MTY3NjYyNDA0OSwidXNlck5vIjoxMDcsInVzZXJJZCI6IlZVUCIsInVzZXJOYW1lIjoi7LWc7ISg7ZWYIiwiaW5sb05vIjoxMDAwMCwidWdycE5vIjoxMDcsInVzZXJNYWlsIjoiY2hvaXNoXzIyQG5hdmVyLmNvbSIsInNlc3Npb25JZCI6IkZYTVNCMTY3NjUyMDg3MjEyNyJ9.8MHa1HagsltAZj1di3wg2IbGNYaIeua0ol5ZCfRfph6RXPRjsEVpCBfYpuQmiuekIJZiRdA7O9JH84Kv2LCrQw");
        //e.g. bearer token= eyJhbGciOiXXXzUxMiJ9.eyJzdWIiOiPyc2hhcm1hQHBsdW1zbGljZS5jb206OjE6OjkwIiwiZXhwIjoxNTM3MzQyNTIxLCJpYXQiOjE1MzY3Mzc3MjF9.O33zP2l_0eDNfcqSQz29jUGJC-_THYsXllrmkFnk85dNRbAw66dyEKBP5dVcFUuNTA8zhA83kk3Y41_qZYx43T

        conn.setRequestProperty("Content-Type","application/json");
        conn.setRequestMethod("GET");



        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        //String output;
        
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String output = gson.toJson(params);

		

		

        StringBuffer response = new StringBuffer();
        while ((output = in.readLine()) != null) {
            response.append(output);
        }

        in.close();
        // printing result from response
        System.out.println("Response:-" + response.toString());
	}
	
	
	public static Result callApi(String callType, String callUrl, Map<String, String> params) {

		HttpURLConnection conn = null;
		// JsonObject responseJson = null;
		String result = "";

		try {
			URL url = new URL(callUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(20000);
			
			conn.setRequestMethod(callType);
			//if(StringUtils.isNotEmpty(params.get("token"))) {
                //byte[] authEncBytes = Base64.encodeBase64(params.get("token").getBytes());
                //String authStringEnc = new String(authEncBytes);
				conn.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJWVVAiLCJhdXRoIjoiUk9MRV9BRE1JTiIsImV4cCI6MTY3NjYyNDA0OSwidXNlck5vIjoxMDcsInVzZXJJZCI6IlZVUCIsInVzZXJOYW1lIjoi7LWc7ISg7ZWYIiwiaW5sb05vIjoxMDAwMCwidWdycE5vIjoxMDcsInVzZXJNYWlsIjoiY2hvaXNoXzIyQG5hdmVyLmNvbSIsInNlc3Npb25JZCI6IkZYTVNCMTY3NjUyMDg3MjEyNyJ9.8MHa1HagsltAZj1di3wg2IbGNYaIeua0ol5ZCfRfph6RXPRjsEVpCBfYpuQmiuekIJZiRdA7O9JH84Kv2LCrQw");
				//conn.setRequestProperty("headers", "{\"Authorization\" : " + params.get("token")+ "}");
			//}
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Transfer-Encoding", "chunked");
			conn.setRequestProperty("Connection", "keep-alive");
			//conn.setRequestProperty("Data-Type", "json");
			

			//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String jsonOutput = gson.toJson(params);

			bw.write(jsonOutput);
			bw.flush();
			bw.close();

			//if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
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
			//return sb.toString();
			br.close();
			
			//스트링을 jsonObject로 변환
			String str = sb.toString();
			 JsonParser parser = new JsonParser();
		        
		        Object obj = parser.parse(str);
		        JsonObject jobj = (JsonObject)parser.parse(str);
	
			return new Result().success(jobj);
			// }
		} catch (MalformedURLException e) {
			//e.printStackTrace();
			  //throw new ServiceException(e.toString());
			return new Result().fail(e.toString(), 400);
		} catch (IOException e) {
			//throw new ServiceException(e.toString());
			return new Result().fail(e.toString(), 400);
		} catch (JsonSyntaxException e) {
			//throw new ServiceException(e.toString());
			return new Result().fail(e.toString(), 400);
		}
		//return result;
		//return new Result().fail(e.toString(), 400);
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