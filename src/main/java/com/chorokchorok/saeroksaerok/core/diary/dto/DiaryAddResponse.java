package com.chorokchorok.saeroksaerok.core.diary.dto;

import com.chorokchorok.saeroksaerok.core.diary.domain.Diary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DiaryAddResponse {

	private String keyword;
	private String textDiary;
	private String pictureDiary;

	public static DiaryAddResponse of(Diary diary) {
		return new DiaryAddResponse(diary.getKeyword(), diary.getTextDiary(), diary.getPictureDiary());
	}
}
