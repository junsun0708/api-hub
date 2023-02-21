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

import com.example.apihub.base.Result;
import com.example.apihub.base.ServiceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * 1. 매개변수는 변경에 대응하기 위해 Map으로 구성 2. 리턴값은 Result객체를 생성하여 동일한 패턴으로 보내준다. 3. data는
 * jsonObject로 보낸다. -> 그럴려고 했으나 jsonObject도 있고 jsonArray도 있음. 그냥 오브젝트로 보내기로 결정
 * 3. get/post만 변경해도 될 줄 알았으나 동작안함. 속성도 변경해줘야 함. 4. get의 param은 보통 url에 포함되나,
 * tisp 는 url + param을 받음, 이건 더 생각해 봐야 할듯. 5. 타사는 토큰값이 없으므로 api-hub에서 토큰값을 받으면
 * 조회시 항상 토큰값을 포함해서 던져주게 고려하였으나 귀찮을것 같음. -> 한번이라도 토큰을 발급받으면 특정디비에 저장하고 해당 url 에서
 * 호출할때 대조하여 정상인 데이터만 보내주게 설계하면 될듯.
 * 
 * @author salva
 *
 *         1. 토큰을 url마다 포함시키는것을 -> 토큰을 특정 디비 또는 메모리에 저장하고 특정url호출시 토큰을 비교하여 처리
 *         2. 토큰을 api허브에서 발급 -> 그러면 기존에것도 수정해야 하므로 추후 고려 3. callDb, callFile 추가
 *         4. 현재는 소량인데 대용량이 될 수도 있음 -> 큐 고려 5. 지속적인 데이터 확인 -> 웹소켓 추가
 */
public class HttpUtil {

	public static Result callApi(String callType, String callUrl, Map<String, String> params) {
		try {
			URL url = new URL(callUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod(callType);
			conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setDoOutput(true);

			if ("GET".equals(callType)) {
				conn.setRequestProperty("Authorization", params.get("token"));
				callMsg(conn);
				return new Result().success(callReader(conn));
			} else {
				callWriter(conn, params);
				callMsg(conn);
				return new Result().success(callReader(conn));
			}
		} catch (MalformedURLException e) {
			return new Result().fail(e.toString());
		} catch (IOException e) {
			return new Result().fail(e.toString());
		} catch (JsonSyntaxException e) {
			return new Result().fail(e.toString());
		}
	}

	public static void loop(Map<String, String> map) {
		System.out.println("=== input값 ===");
		for (String key : map.keySet()) {
			System.out.println(key + " , " + map.get(key));
		}
		// keyset보다 속력이 느려 람다는 사용 안함.
		// map.forEach((key, value) -> System.out.println(key + " , " + map.get(key)));
	}

	// 리턴은 오브젝트로 한다. 리턴값이 다양해서 파싱에러가 생길 수 있음.
	private static Object callReader(HttpURLConnection conn) throws IOException {
		try {
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();

				JsonParser parser = new JsonParser();
				return parser.parse(sb.toString());
				// JsonObject jobj = (JsonObject)parser.parse(sb.toString());
			} else {
				throw new ServiceException(conn.getResponseMessage(), conn.getResponseCode());
			}

		} catch (Exception e) {
			throw new ServiceException(conn.getResponseMessage(), conn.getResponseCode());
		}
	}

	private static void callWriter(HttpURLConnection conn, Map<String, String> params) throws IOException {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String jsonOutput = gson.toJson(params);

			bw.write(jsonOutput);
			bw.flush();
			bw.close();
		} catch (Exception e) {
			throw new ServiceException(conn.getResponseMessage(), conn.getResponseCode());
		}
	}

	private static void callMsg(HttpURLConnection conn) throws IOException {
		try {
			System.out.println("getContentType():" + conn.getContentType()); // 응답 콘텐츠 유형 구하기
			System.out.println("getResponseCode():" + conn.getResponseCode()); // 응답 코드 구하기
			System.out.println("getResponseMessage():" + conn.getResponseMessage()); // 응답 메시지
		} catch (Exception e) {
			throw new ServiceException(conn.getResponseMessage(), conn.getResponseCode());
		}
	}

}