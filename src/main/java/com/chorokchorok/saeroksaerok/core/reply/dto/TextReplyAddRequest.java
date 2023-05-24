package com.chorokchorok.saeroksaerok.core.reply.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class TextReplyAddRequest {

	private long diaryId;
	private MultipartFile audioReply;

	public TextReplyAddRequest(long diaryId, MultipartFile audioReply) {
		this.diaryId = diaryId;
		this.audioReply = audioReply;
	}
}
