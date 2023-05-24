package com.chorokchorok.saeroksaerok.core.reply.dto;

import com.chorokchorok.saeroksaerok.core.reply.domain.Emoji;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmojiReplyAddRequest {

	private long diaryId;
	private Emoji emoji;
}
