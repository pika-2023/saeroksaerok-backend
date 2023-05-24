package com.chorokchorok.saeroksaerok.core.reply.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chorokchorok.saeroksaerok.core.common.service.SpeechRecognitionService;
import com.chorokchorok.saeroksaerok.core.diary.domain.Diary;
import com.chorokchorok.saeroksaerok.core.diary.domain.DiaryRepository;
import com.chorokchorok.saeroksaerok.core.profile.domain.Profile;
import com.chorokchorok.saeroksaerok.core.profile.domain.ProfileRepository;
import com.chorokchorok.saeroksaerok.core.reply.domain.EmojiReply;
import com.chorokchorok.saeroksaerok.core.reply.domain.EmojiReplyRepository;
import com.chorokchorok.saeroksaerok.core.reply.domain.TextReply;
import com.chorokchorok.saeroksaerok.core.reply.domain.TextReplyRepository;
import com.chorokchorok.saeroksaerok.core.reply.dto.EmojiReplyAddRequest;
import com.chorokchorok.saeroksaerok.core.reply.dto.EmojiReplyAddResponse;
import com.chorokchorok.saeroksaerok.core.reply.dto.TextReplyAddRequest;
import com.chorokchorok.saeroksaerok.core.reply.dto.TextReplyAddResponse;
import com.chorokchorok.saeroksaerok.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReplyServiceImpl implements ReplyService {

	private final TextReplyRepository textReplyRepository;
	private final EmojiReplyRepository emojiReplyRepository;
	private final DiaryRepository diaryRepository;
	private final ProfileRepository profileRepository;
	private final SpeechRecognitionService speechRecognitionService;

	@Transactional
	@Override
	public TextReplyAddResponse addTextReply(long profileId, TextReplyAddRequest request) {
		// find diary
		Diary diary = diaryRepository.findById(request.getDiaryId())
			.orElseThrow(() -> new NotFoundException("diary", request.getDiaryId()));

		// transcribe audio to text
		String text = speechRecognitionService.transcribeAudioToText(request.getAudioReply());

		// find profile
		Profile profile = profileRepository.findById(profileId)
			.orElseThrow(() -> new NotFoundException("profile", profileId));

		// create textReply
		TextReply textReply = new TextReply(profile, diary, text);

		// save textReply
		TextReply savedTextReply = textReplyRepository.save(textReply);

		// create and return response
		return TextReplyAddResponse.of(savedTextReply);
	}

	@Transactional
	@Override
	public EmojiReplyAddResponse addEmojiReply(long profileId, EmojiReplyAddRequest request) {
		// find diary
		Diary diary = diaryRepository.findById(request.getDiaryId())
			.orElseThrow(() -> new NotFoundException("diary", request.getDiaryId()));

		// find profile
		Profile profile = profileRepository.findById(profileId)
			.orElseThrow(() -> new NotFoundException("profile", profileId));

		// create emojiReply
		EmojiReply emojiReply = new EmojiReply(profile, diary, request.getEmoji());

		// save emojiReply
		EmojiReply savedEmojiReply = emojiReplyRepository.save(emojiReply);

		// create and return response
		return EmojiReplyAddResponse.of(savedEmojiReply);
	}
}
