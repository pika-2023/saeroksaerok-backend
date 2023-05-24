package com.chorokchorok.saeroksaerok.core.diary.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.chorokchorok.saeroksaerok.core.diary.domain.Diary;
import com.chorokchorok.saeroksaerok.core.profile.domain.Profile;
import com.chorokchorok.saeroksaerok.core.reply.dto.EmojiRepliesSearchResponse;
import com.chorokchorok.saeroksaerok.core.reply.dto.TextRepliesSearchResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DiarySearchResponse {

	private long id;
	private String author;
	private String keyword;
	private String textDiary;
	private String pictureDiary;
	private LocalDateTime createdAt;
	private List<TextRepliesSearchResponse> textReplies;
	private List<EmojiRepliesSearchResponse> emojiReplies;
	private boolean myDiary;

	public static DiarySearchResponse of(
		Diary diary,
		Profile profile,
		List<TextRepliesSearchResponse> textReplies,
		List<EmojiRepliesSearchResponse> emojiReplies
	) {
		return new DiarySearchResponse(
			diary.getId(),
			diary.getProfile().getNickname(),
			diary.getKeyword(),
			diary.getTextDiary(),
			diary.getPictureDiary(),
			diary.getCreatedAt(),
			textReplies,
			emojiReplies,
			diary.getProfile().getId() == profile.getId() ? true : false
		);
	}
}
