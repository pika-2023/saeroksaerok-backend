package com.chorokchorok.saeroksaerok.core.user.service;

import com.chorokchorok.saeroksaerok.core.user.dto.EmailDuplicatedCheckRequest;
import com.chorokchorok.saeroksaerok.core.user.dto.EmailDuplicatedCheckResponse;
import com.chorokchorok.saeroksaerok.core.user.dto.SignUpRequest;
import com.chorokchorok.saeroksaerok.core.user.dto.SignUpResponse;

public interface UserService {
	SignUpResponse signUp(SignUpRequest request);

	EmailDuplicatedCheckResponse checkEmailDuplicated(EmailDuplicatedCheckRequest request);
}
