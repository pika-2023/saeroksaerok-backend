package com.chorokchorok.saeroksaerok.core.reply.dto;

import java.time.LocalDateTime;

import com.chorokchorok.saeroksaerok.core.reply.domain.Emoji;
import com.chorokchorok.saeroksaerok.core.reply.domain.EmojiReply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmojiReplyAddResponse {

	private long emojiReplyId;
	private String author;
	private Emoji emojiReply;
	private LocalDateTime createdAt;

	public static EmojiReplyAddResponse of(EmojiReply emojiReply) {
		return new EmojiReplyAddResponse(
			emojiReply.getId(),
			emojiReply.getProfile().getNickname(),
			emojiReply.getEmoji(),
			emojiReply.getCreatedAt()
		);
	}
}
