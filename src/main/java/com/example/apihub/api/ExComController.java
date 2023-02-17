package com.example.apihub.api;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apihub.base.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "그외", description = "그외 관련 api 입니다.")
@RestController
@RequestMapping("/excom")
public interface ExComController {

	@Operation(summary = "그외 메서드", description = "그외 메서드입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Result.class))),
			@ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = Result.class))) })
	@PostMapping("/dash")
	public Result getDash(@RequestBody Map<String, String> input);
	
	
	@Operation(summary = "그외 메서드", description = "그외 메서드입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Result.class))),
			@ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = Result.class))) })
	@PostMapping("/dash1")
	public Result getDash1(@RequestBody Map<String, String> input);
}