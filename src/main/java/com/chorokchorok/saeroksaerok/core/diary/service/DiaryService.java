package com.chorokchorok.saeroksaerok.core.diary.service;

import com.chorokchorok.saeroksaerok.core.diary.dto.DiaryAddRequest;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiaryAddResponse;
import com.chorokchorok.saeroksaerok.core.diary.dto.KeywordDrawResponse;

public interface DiaryService {

	KeywordDrawResponse drawKeyword();

	DiaryAddResponse addDiary(long profileId, DiaryAddRequest request);
}
