package com.chorokchorok.saeroksaerok.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chorokchorok.saeroksaerok.core.common.SecurityUser;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiariesSearchResponse;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiaryAddRequest;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiaryAddResponse;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiarySearchResponse;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiaryShareRequest;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiaryShareResponse;
import com.chorokchorok.saeroksaerok.core.diary.dto.KeywordDrawResponse;
import com.chorokchorok.saeroksaerok.core.diary.dto.SearchType;
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

	@PostMapping("/share")
	public DiaryShareResponse shareDiary(
		@AuthenticationPrincipal SecurityUser securityUser,
		@RequestBody DiaryShareRequest request
	) {
		return diaryService.shareDiary(securityUser.getProfileId(), request);
	}

	@GetMapping
	public Map<String, List<DiariesSearchResponse>> searchDiaries(
		@AuthenticationPrincipal SecurityUser securityUser,
		@RequestParam("searchType") SearchType searchType
	) {
		List<DiariesSearchResponse> data = diaryService.searchDiaries(securityUser.getProfileId(), searchType);
		return Map.of("data", data);
	}

	@GetMapping("/{diaryId}")
	public DiarySearchResponse searchDiary(
		@AuthenticationPrincipal SecurityUser securityUser,
		@PathVariable Long diaryId
	) {
		return diaryService.searchDiary(securityUser.getProfileId(), diaryId);
	}
}
