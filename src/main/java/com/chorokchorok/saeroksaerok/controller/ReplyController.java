package com.chorokchorok.saeroksaerok.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chorokchorok.saeroksaerok.core.common.SecurityUser;
import com.chorokchorok.saeroksaerok.core.reply.dto.EmojiReplyAddRequest;
import com.chorokchorok.saeroksaerok.core.reply.dto.EmojiReplyAddResponse;
import com.chorokchorok.saeroksaerok.core.reply.dto.TextReplyAddRequest;
import com.chorokchorok.saeroksaerok.core.reply.dto.TextReplyAddResponse;
import com.chorokchorok.saeroksaerok.core.reply.service.ReplyService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/replies")
@RestController
public class ReplyController {

	private final ReplyService replyService;

	@PostMapping("/text")
	public ResponseEntity<TextReplyAddResponse> addTextReply(
		@AuthenticationPrincipal SecurityUser securityUser,
		TextReplyAddRequest request
	) {
		TextReplyAddResponse body = replyService.addTextReply(securityUser.getProfileId(), request);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(body);
	}

	@PostMapping("/emoji")
	public ResponseEntity<EmojiReplyAddResponse> addEmojiReply(
		@AuthenticationPrincipal SecurityUser securityUser,
		@RequestBody EmojiReplyAddRequest request
	) {
		EmojiReplyAddResponse body = replyService.addEmojiReply(securityUser.getProfileId(), request);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(body);
	}
}
