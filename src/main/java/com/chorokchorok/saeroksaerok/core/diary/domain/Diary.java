package com.chorokchorok.saeroksaerok.core.diary.domain;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.chorokchorok.saeroksaerok.core.common.BaseEntity;
import com.chorokchorok.saeroksaerok.core.profile.domain.Profile;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "diary")
@Entity
public class Diary extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(updatable = false)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id")
	private Profile profile;

	private String keyword;

	@Column(columnDefinition = "TEXT")
	private String pictureDiary;

	@Column(columnDefinition = "TEXT")
	private String textDiary;

	public Diary(Profile profile, String keyword, String pictureDiary, String textDiary) {
		this.profile = profile;
		this.keyword = keyword;
		this.pictureDiary = pictureDiary;
		this.textDiary = textDiary;
	}
}
