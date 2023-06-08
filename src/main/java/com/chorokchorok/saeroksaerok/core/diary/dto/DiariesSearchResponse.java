package com.chorokchorok.saeroksaerok.core.diary.dto;

import java.time.LocalDateTime;

import com.chorokchorok.saeroksaerok.core.diary.domain.Diary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DiariesSearchResponse {

	private long id;
	private String author;
	private String keyword;
	private String textDiary;
	private String pictureDiary;
	private String profileImageUrl;
	private LocalDateTime createdAt;

	public static DiariesSearchResponse of(Diary diary) {
		return new DiariesSearchResponse(
			diary.getId(),
			diary.getProfile().getNickname(),
			diary.getKeyword(),
			diary.getTextDiary(),
			diary.getPictureDiary(),
			diary.getProfile().getProfileImage(),
			diary.getCreatedAt()
		);
	}
}
