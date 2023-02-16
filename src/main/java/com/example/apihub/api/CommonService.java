package com.example.apihub.api;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.apihub.core.HttpUtil;
import com.example.apihub.core.RequestMethod;

@Service
public class CommonService implements CommonController {
	@Override
	public String getToken(Map<String, String> input) {
		HttpUtil.loop(input);
		return HttpUtil.callApi(RequestMethod.POST, "", input);
	}
}
