package com.chorokchorok.saeroksaerok.core.reply.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TextReplyRepository extends JpaRepository<TextReply, Long> {

	List<TextReply> findAllByDiaryId(long diaryId);
}
