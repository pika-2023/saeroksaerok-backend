package com.chorokchorok.saeroksaerok.core.reply.dto;

import java.time.LocalDateTime;

import com.chorokchorok.saeroksaerok.core.reply.domain.TextReply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TextRepliesSearchResponse {

	private long textReplyId;
	private String author;
	private String textReply;
	private LocalDateTime createdAt;

	public static TextRepliesSearchResponse of(TextReply textReply) {
		return new TextRepliesSearchResponse(
			textReply.getId(),
			textReply.getProfile().getNickname(),
			textReply.getTextReply(),
			textReply.getCreatedAt()
		);
	}
}
