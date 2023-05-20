package com.chorokchorok.saeroksaerok.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chorokchorok.saeroksaerok.core.user.dto.EmailDuplicatedCheckRequest;
import com.chorokchorok.saeroksaerok.core.user.dto.EmailDuplicatedCheckResponse;
import com.chorokchorok.saeroksaerok.core.user.dto.SignInRequest;
import com.chorokchorok.saeroksaerok.core.user.dto.SignInResponse;
import com.chorokchorok.saeroksaerok.core.user.dto.SignUpRequest;
import com.chorokchorok.saeroksaerok.core.user.dto.SignUpResponse;
import com.chorokchorok.saeroksaerok.core.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<SignUpResponse> signUp(
		@RequestBody SignUpRequest request
	) {
		SignUpResponse body = userService.signUp(request);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(body);
	}

	@PostMapping("/signup/email/duplicated")
	public EmailDuplicatedCheckResponse emailDuplicatedCheck(
		@RequestBody EmailDuplicatedCheckRequest request
	) {
		return userService.checkEmailDuplicated(request);
	}

	@PostMapping("/signin")
	public SignInResponse signIn(
		@RequestBody SignInRequest request
	) {
		return userService.signIn(request);
	}
}
