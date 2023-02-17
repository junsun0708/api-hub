package com.example.apihub.api;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.apihub.base.Result;
import com.example.apihub.core.HttpUtil;
import com.example.apihub.core.RequestMethod;

@Service
public class ExComService implements ExComController {
	@Override
	public Result getDash(Map<String, String> input) {
		HttpUtil.loop(input);
		//return HttpUtil.callApi(RequestMethod.GET, "http://175.123.142.155:18181/api/fxUiDash/dash", input);
		return HttpUtil.callApi(RequestMethod.GET, "http://localhost:8080/api/fxUiDash/dash/1", input);
	}
	
	@Override
	public Result getDash1(Map<String, String> input) {
		HttpUtil.loop(input);
		//return HttpUtil.callApi(RequestMethod.GET, "http://175.123.142.155:18181/api/fxUiDash/dash", input);
		try {
			HttpUtil.callApi2(input);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return new Result();
		
	}
}
