package com.chorokchorok.saeroksaerok.core.profile.dto;

import com.chorokchorok.saeroksaerok.core.profile.domain.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfileAddResponse {

	private String nickname;
	private String profileImageUrl;

	public static ProfileAddResponse ofEntity(Profile profile) {
		return new ProfileAddResponse(profile.getNickname(), profile.getProfileImage());
	}
}
