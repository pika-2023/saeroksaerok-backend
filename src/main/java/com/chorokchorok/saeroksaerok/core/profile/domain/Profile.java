package com.chorokchorok.saeroksaerok.core.profile.domain;

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
import com.chorokchorok.saeroksaerok.core.user.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "profile")
@Entity
public class Profile extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(updatable = false)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private String nickname;

	private String profileImage;

	public Profile(User user, String nickname, String profileImage) {
		this.user = user;
		this.nickname = nickname;
		this.profileImage = profileImage;
	}
}
