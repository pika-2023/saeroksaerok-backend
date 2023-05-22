package com.chorokchorok.saeroksaerok.core.diary.service;

import org.springframework.stereotype.Service;

import com.chorokchorok.saeroksaerok.core.common.service.SpeechRecognitionService;
import com.chorokchorok.saeroksaerok.core.diary.domain.Diary;
import com.chorokchorok.saeroksaerok.core.diary.domain.DiaryRepository;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiaryAddRequest;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiaryAddResponse;
import com.chorokchorok.saeroksaerok.core.diary.dto.KeywordDrawResponse;
import com.chorokchorok.saeroksaerok.core.profile.domain.Profile;
import com.chorokchorok.saeroksaerok.core.profile.domain.ProfileRepository;
import com.chorokchorok.saeroksaerok.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DiaryServiceImpl implements DiaryService {

	private final SpeechRecognitionService speechRecognitionService;
	private final TranslationService translationService;
	private final ImageTranscriptService imageTranscriptService;
	private final KeywordDrawerService keywordDrawerService;
	private final DiaryRepository diaryRepository;
	private final ProfileRepository profileRepository;

	@Override
	public KeywordDrawResponse drawKeyword() {
		// draw keyword
		String keyword = keywordDrawerService.keywordDraw();

		// create and return response
		return new KeywordDrawResponse(keyword);
	}

	@Override
	public DiaryAddResponse addDiary(long profileId, DiaryAddRequest request) {
		// find profile
		Profile profile = profileRepository.findById(profileId)
			.orElseThrow(() -> new NotFoundException("profile", profileId));

		// transcribe audio to text
		String textDiary = speechRecognitionService.transcribeAudioToText(request.getAudioDiary());

		// translate korean to english
		String prompt = translationService.translate(textDiary);

		// transcribe text to picture
		String pictureDiary = imageTranscriptService.transcibe(prompt);

		// create diary
		Diary diary = new Diary(
			profile,
			request.getKeyword(),
			pictureDiary,
			textDiary
		);

		// save diary
		Diary savedDiary = diaryRepository.save(diary);

		// create and return response
		return DiaryAddResponse.of(savedDiary);
	}
}
