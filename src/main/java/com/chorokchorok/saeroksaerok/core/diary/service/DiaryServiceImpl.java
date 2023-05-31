package com.chorokchorok.saeroksaerok.core.diary.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chorokchorok.saeroksaerok.core.common.service.SpeechRecognitionService;
import com.chorokchorok.saeroksaerok.core.diary.domain.Diary;
import com.chorokchorok.saeroksaerok.core.diary.domain.DiaryRepository;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiariesSearchResponse;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiaryAddRequest;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiaryAddResponse;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiarySearchResponse;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiaryShareRequest;
import com.chorokchorok.saeroksaerok.core.diary.dto.DiaryShareResponse;
import com.chorokchorok.saeroksaerok.core.diary.dto.KeywordDrawResponse;
import com.chorokchorok.saeroksaerok.core.diary.dto.SearchType;
import com.chorokchorok.saeroksaerok.core.profile.domain.Profile;
import com.chorokchorok.saeroksaerok.core.profile.domain.ProfileRepository;
import com.chorokchorok.saeroksaerok.core.reply.domain.EmojiReplyRepository;
import com.chorokchorok.saeroksaerok.core.reply.domain.TextReplyRepository;
import com.chorokchorok.saeroksaerok.core.reply.dto.EmojiRepliesSearchResponse;
import com.chorokchorok.saeroksaerok.core.reply.dto.TextRepliesSearchResponse;
import com.chorokchorok.saeroksaerok.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DiaryServiceImpl implements DiaryService {

	private final SpeechRecognitionService speechRecognitionService;
	private final TranslationService translationService;
	private final ImageTranscriptService imageTranscriptService;
	private final KeywordDrawerService keywordDrawerService;
	private final DiaryRepository diaryRepository;
	private final ProfileRepository profileRepository;
	private final TextReplyRepository textReplyRepository;
	private final EmojiReplyRepository emojiReplyRepository;

	@Override
	public KeywordDrawResponse drawKeyword() {
		// draw keyword
		String keyword = keywordDrawerService.keywordDraw();

		// create and return response
		return new KeywordDrawResponse(keyword);
	}

	@Override
	public DiaryAddResponse addDiary(long profileId, DiaryAddRequest request) {
		// transcribe speech to text
		String firstTextDiary = speechRecognitionService.transcribeAudioToText(request.getFirstAnswer());
		String secondTextDiary = speechRecognitionService.transcribeAudioToText(request.getSecondAnswer());
		String thirdTextDiary = speechRecognitionService.transcribeAudioToText(request.getThirdAnswer());

		// translate korean to english
		String firstTranslation = translationService.translate(firstTextDiary);
		String secondTranslation = translationService.translate(secondTextDiary);
		String thirdTranslation = translationService.translate(thirdTextDiary);

		// transcribe text to picture
		String translation = firstTranslation + secondTranslation + thirdTranslation;
		String pictureDiary = imageTranscriptService.transcibe(translation);

		// create and return response
		String textDiary = firstTextDiary + secondTextDiary + thirdTextDiary;
		return new DiaryAddResponse(request.getKeyword(), textDiary, pictureDiary);
	}

	@Transactional
	@Override
	public DiaryShareResponse shareDiary(long profileId, DiaryShareRequest request) {
		// find profile
		Profile profile = profileRepository.findById(profileId)
			.orElseThrow(() -> new NotFoundException("profile", profileId));

		// create diary
		Diary diary = new Diary(
			profile,
			request.getKeyword(),
			request.getPictureDiary(),
			request.getTextDiary()
		);

		// save diary
		Diary savedDiary = diaryRepository.save(diary);

		// create and return diary
		return DiaryShareResponse.of(savedDiary);
	}

	@Override
	public List<DiariesSearchResponse> searchDiaries(long profileId, SearchType searchType) {
		if (searchType.equals(SearchType.MY)) {
			return diaryRepository.findAllByProfileIdOrderByCreatedAtDesc(profileId).stream()
				.map(diary -> DiariesSearchResponse.of(diary))
				.collect(Collectors.toList());
		}
		return diaryRepository.findAllByDeletedAtNullOrderByCreatedAtDesc().stream()
			.map(diary -> DiariesSearchResponse.of(diary))
			.collect(Collectors.toList());
	}

	@Override
	public DiarySearchResponse searchDiary(long profileId, long diaryId) {
		// find profile
		Profile profile = profileRepository.findById(profileId)
			.orElseThrow(() -> new NotFoundException("profile", profileId));

		// find diary
		Diary diary = diaryRepository.findById(diaryId)
			.orElseThrow(() -> new NotFoundException("diary", diaryId));

		// find textReplies
		List<TextRepliesSearchResponse> textReplies = textReplyRepository.findAllByDiaryId(diaryId).stream()
			.map(textReply -> TextRepliesSearchResponse.of(textReply))
			.collect(Collectors.toList());

		// find emojiReplies
		List<EmojiRepliesSearchResponse> emojiReplies = emojiReplyRepository.findAllByDiaryId(diaryId).stream()
			.map(emojiReply -> EmojiRepliesSearchResponse.of(emojiReply))
			.collect(Collectors.toList());

		// create and return response
		return DiarySearchResponse.of(diary, profile, textReplies, emojiReplies);
	}
}
