package com.chorokchorok.saeroksaerok.core.reply.dto;

import java.time.LocalDateTime;

import com.chorokchorok.saeroksaerok.core.reply.domain.TextReply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TextReplyAddResponse {

	private long textReplyId;
	private String author;
	private String textReply;
	private String audioUrl;
	private LocalDateTime createdAt;

	public static TextReplyAddResponse of(TextReply textReply) {
		return new TextReplyAddResponse(
			textReply.getId(),
			textReply.getProfile().getNickname(),
			textReply.getTextReply(),
			textReply.getAudio(),
			textReply.getCreatedAt()
		);
	}
}
