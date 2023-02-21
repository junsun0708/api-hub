package com.example.apihub.api;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.apihub.base.Result;
import com.example.apihub.core.HttpUtil;
import com.example.apihub.core.RequestMethod;

@Service
public class CommonService implements CommonController {
	@Override
	public Result getToken(Map<String, String> input) {
		HttpUtil.loop(input);
		return HttpUtil.callApi(RequestMethod.POST, "http://10.0.1.11:18181/api/auth/login", input);
	}
}
