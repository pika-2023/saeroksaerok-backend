package com.chorokchorok.saeroksaerok.core.diary.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class DiaryAddRequest {

	private String keyword;
	private MultipartFile firstAnswer;
	private MultipartFile secondAnswer;
	private MultipartFile thirdAnswer;

	public DiaryAddRequest(
		String keyword,
		MultipartFile firstAnswer,
		MultipartFile secondAnswer,
		MultipartFile thirdAnswer
	) {
		this.keyword = keyword;
		this.firstAnswer = firstAnswer;
		this.secondAnswer = secondAnswer;
		this.thirdAnswer = thirdAnswer;
	}
}
