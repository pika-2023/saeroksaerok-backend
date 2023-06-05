package com.chorokchorok.saeroksaerok.core.diary.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
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
		log.info("\n--------------------------------------------");
		this.firstAnswer = firstAnswer;
		log.info("파일 형식: ", firstAnswer.getOriginalFilename());
		this.secondAnswer = secondAnswer;
		log.info("파일 형식: ", secondAnswer.getOriginalFilename());
		this.thirdAnswer = thirdAnswer;
		log.info("파일 형식: ", thirdAnswer.getOriginalFilename());
		log.info("--------------------------------------------\n");
	}
}
