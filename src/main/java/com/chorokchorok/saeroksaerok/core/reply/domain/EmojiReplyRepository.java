package com.chorokchorok.saeroksaerok.core.reply.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmojiReplyRepository extends JpaRepository<EmojiReply, Long> {
	List<EmojiReply> findAllByDiaryId(long diaryId);
}
