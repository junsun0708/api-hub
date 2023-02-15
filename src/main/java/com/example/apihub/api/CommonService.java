package com.example.apihub.api;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.apihub.core.HttpUtil;

@Service
public class CommonService implements CommonController {

	@Override
	public String getToken(Map<String, String> input) {
		HttpUtil.loop(input);
		return null;
	}
}
