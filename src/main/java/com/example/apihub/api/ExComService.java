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
		return HttpUtil.callApi(RequestMethod.GET, "http://10.0.1.11:18181/api/fxUiDash/dash/1", input);
	}
	
	@Override
	public Result getDash1(Map<String, String> input) {
		HttpUtil.loop(input);
		return HttpUtil.callApi(RequestMethod.POST, "http://10.0.1.11:18181/api/feEngBas/list", input);
	}
}
