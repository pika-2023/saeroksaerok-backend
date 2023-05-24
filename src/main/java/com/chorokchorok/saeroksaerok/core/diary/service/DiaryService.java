package com.chorokchorok.saeroksaerok.core.diary.service;

import java.util.List;

import com.chorokchorok.saeroksaerok.core.diary.dto.DiariesSearchResponse;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiaryAddRequest;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiaryAddResponse;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiarySearchResponse;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiaryShareRequest;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiaryShareResponse;
import com.chorokchorok.saeroksaerok.core.diary.dto.KeywordDrawResponse;
import com.chorokchorok.saeroksaerok.core.diary.dto.SearchType;

public interface DiaryService {

	KeywordDrawResponse drawKeyword();

	DiaryAddResponse addDiary(long profileId, DiaryAddRequest request);

	DiaryShareResponse shareDiary(long profileId, DiaryShareRequest request);

	List<DiariesSearchResponse> searchDiaries(long profileId, SearchType searchType);

	DiarySearchResponse searchDiary(long profileId, long diaryId);
}
