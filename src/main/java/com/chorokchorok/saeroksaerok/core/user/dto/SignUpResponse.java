package com.chorokchorok.saeroksaerok.core.user.dto;

import com.chorokchorok.saeroksaerok.core.user.domain.Email;
import com.chorokchorok.saeroksaerok.core.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpResponse {

	private Email email;

	public static SignUpResponse ofEntity(User user) {
		return new SignUpResponse(user.getEmail());
	}
}
