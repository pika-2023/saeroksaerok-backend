package com.chorokchorok.saeroksaerok.core.user.dto;

import com.chorokchorok.saeroksaerok.core.user.domain.Email;
import com.chorokchorok.saeroksaerok.core.user.domain.Password;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpRequest {

	private Email email;
	private Password password;
}
