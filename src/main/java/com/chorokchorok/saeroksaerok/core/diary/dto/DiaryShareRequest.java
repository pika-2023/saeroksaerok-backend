package com.chorokchorok.saeroksaerok.core.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DiaryShareRequest {

	private String keyword;
	private String textDiary;
	private String pictureDiary;
}
