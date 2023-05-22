package com.chorokchorok.saeroksaerok.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chorokchorok.saeroksaerok.core.common.SecurityUser;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiaryAddRequest;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiaryAddResponse;
import com.chorokchorok.saeroksaerok.core.diary.dto.KeywordDrawResponse;
import com.chorokchorok.saeroksaerok.core.diary.service.DiaryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/diaries")
@RestController
public class DiaryController {

	private final DiaryService diaryService;

	@GetMapping("/keyword/draw")
	public KeywordDrawResponse drawKeyword() {
		return diaryService.drawKeyword();
	}

	@PostMapping
	public ResponseEntity<DiaryAddResponse> addDiary(
		@AuthenticationPrincipal SecurityUser securityUser,
		DiaryAddRequest request
	) {
		DiaryAddResponse body = diaryService.addDiary(securityUser.getProfileId(), request);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(body);
	}
}