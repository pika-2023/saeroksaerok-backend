package com.chorokchorok.saeroksaerok.core.reply.service;

import com.chorokchorok.saeroksaerok.core.reply.dto.EmojiReplyAddRequest;
import com.chorokchorok.saeroksaerok.core.reply.dto.EmojiReplyAddResponse;
import com.chorokchorok.saeroksaerok.core.reply.dto.TextReplyAddRequest;
import com.chorokchorok.saeroksaerok.core.reply.dto.TextReplyAddResponse;

public interface ReplyService {

	TextReplyAddResponse addTextReply(long profileId, TextReplyAddRequest request);

	EmojiReplyAddResponse addEmojiReply(long profileId, EmojiReplyAddRequest request);
}
