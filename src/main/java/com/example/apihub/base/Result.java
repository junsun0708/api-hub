package com.example.apihub.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class Result {
	
	@Schema(description = "응답 코드", example = "200")
	private int code;
	
	@Schema(description = "응답 메시지", example = "Success")
	private String message;
	
	@Schema(description = "응답 데이터", example = "Object")
	private Object data;

	private Result setResult(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
		return this;
	}

	public Result success() {
		return setResult(200, "Success", null);
	}

	public Result success(Object data) {
		return setResult(200, "Success", data);
	}
	
	public Result fail(String message) {
		return setResult(400, message, null);
	}

	public Result fail(Object data, String message) {
		return setResult(400, message, data);
	}

	public Result fail(Object data, String message, int code) {
		return setResult(code, message, data);
	}

	public Result fail(String message, int code) {
		return setResult(code, message, null);
	}
}
