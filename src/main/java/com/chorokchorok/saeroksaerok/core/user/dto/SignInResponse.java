package com.chorokchorok.saeroksaerok.core.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignInResponse {

	private String email;
	private String accessToken;
	private String refreshToken;
}
