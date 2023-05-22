package com.chorokchorok.saeroksaerok.core.diary.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class DiaryAddRequest {

	private String keyword;
	private MultipartFile audioDiary;

	public DiaryAddRequest(String keyword, MultipartFile audioDiary) {
		this.keyword = keyword;
		this.audioDiary = audioDiary;
	}
}
