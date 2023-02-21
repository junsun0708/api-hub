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

@Tag(name = "인증", description = "인증 관련 api 입니다.")
@RestController
@RequestMapping("/auth")
public interface CommonController {

	@Operation(summary = "로그인 메서드", description = "로그인 메서드입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Result.class))),
			@ApiResponse(responseCode = "400", description = "bad request operation", content = @Content(schema = @Schema(implementation = Result.class))) })
	@PostMapping("/token")
	public Result getToken(@io.swagger.v3.oas.annotations.parameters.RequestBody (description = "{\r\n"
			+ "  \"email\": \"VUP\",\r\n"
			+ "  \"password\": \"1111\"\r\n"
			+ "}") @RequestBody Map<String, String> input);
	
	/*
	 *                 content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = Long::class),
                    examples = [ExampleObject(name = "name", value = "1648787755769")]
                )]
	 */
}