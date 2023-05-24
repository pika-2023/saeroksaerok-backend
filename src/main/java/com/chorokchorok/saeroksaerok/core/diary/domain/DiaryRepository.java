package com.chorokchorok.saeroksaerok.core.diary.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

	List<Diary> findAllByProfileIdOrderByCreatedAtDesc(long profileId);

	List<Diary> findAllByDeletedAtNullOrderByCreatedAtDesc();
}
