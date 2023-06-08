package com.chorokchorok.saeroksaerok.core.reply.dto;

import com.chorokchorok.saeroksaerok.core.reply.domain.Emoji;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class EmojiReplyAddRequest {

	private long diaryId;
	private Emoji emoji;

	public EmojiReplyAddRequest(long diaryId, Emoji emoji) {
		log.info("");
		log.info("000000" + diaryId + "000000");
		log.info("00000014000000");
		log.info("");
		this.diaryId = diaryId;
		this.emoji = emoji;
	}
}
