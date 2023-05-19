package com.chorokchorok.saeroksaerok.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chorokchorok.saeroksaerok.core.profile.dto.ProfileAddRequest;
import com.chorokchorok.saeroksaerok.core.profile.dto.ProfileAddResponse;
import com.chorokchorok.saeroksaerok.core.profile.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/profiles")
@RestController
public class ProfileController {

	private final ProfileService profileService;

	@PostMapping
	public ResponseEntity<ProfileAddResponse> addProfile(ProfileAddRequest request) {
		ProfileAddResponse body = profileService.addProfile(request);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(body);
	}
}
