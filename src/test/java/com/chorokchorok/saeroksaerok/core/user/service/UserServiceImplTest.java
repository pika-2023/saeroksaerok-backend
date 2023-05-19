package com.chorokchorok.saeroksaerok.core.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.chorokchorok.saeroksaerok.core.user.domain.Email;
import com.chorokchorok.saeroksaerok.core.user.domain.Password;
import com.chorokchorok.saeroksaerok.core.user.dto.EmailDuplicatedCheckRequest;
import com.chorokchorok.saeroksaerok.core.user.dto.EmailDuplicatedCheckResponse;
import com.chorokchorok.saeroksaerok.core.user.dto.SignUpRequest;
import com.chorokchorok.saeroksaerok.core.user.dto.SignUpResponse;

@Transactional
@SpringBootTest
class UserServiceImplTest {

	@Autowired
	private UserServiceImpl sut;

	@Test
	void 회원가입_성공() {
		// given
		SignUpRequest request = new SignUpRequest(
			Email.of("test@email.com"),
			Password.of("passw0rd")
		);

		// when
		SignUpResponse response = sut.signUp(request);

		// then
		assertThat(response.getEmail().getValue()).isEqualTo("test@email.com");
	}

	@Test
	void 이메일_중복확인_성공() {
		// given
		SignUpRequest signUpRequest = new SignUpRequest(
			Email.of("test@email.com"),
			Password.of("passw0rd")
		);
		sut.signUp(signUpRequest);

		EmailDuplicatedCheckRequest duplicatedRequest =
			new EmailDuplicatedCheckRequest(Email.of("test@email.com"));
		EmailDuplicatedCheckRequest unduplicatedRequest =
			new EmailDuplicatedCheckRequest(Email.of("test@email.org"));

		// when
		EmailDuplicatedCheckResponse duplicatedResponse = sut.checkEmailDuplicated(duplicatedRequest);
		EmailDuplicatedCheckResponse unduplicatedResponse = sut.checkEmailDuplicated(unduplicatedRequest);

		// then
		assertThat(duplicatedResponse.isDuplicated()).isEqualTo(true);
		assertThat(unduplicatedResponse.isDuplicated()).isEqualTo(false);
	}
}
