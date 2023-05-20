package com.chorokchorok.saeroksaerok.core.profile.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import com.chorokchorok.saeroksaerok.core.profile.dto.ProfileAddRequest;
import com.chorokchorok.saeroksaerok.core.profile.dto.ProfileAddResponse;
import com.chorokchorok.saeroksaerok.core.user.domain.Email;
import com.chorokchorok.saeroksaerok.core.user.domain.Password;
import com.chorokchorok.saeroksaerok.core.user.dto.SignUpRequest;
import com.chorokchorok.saeroksaerok.core.user.service.UserService;

@Transactional
@SpringBootTest
class ProfileServiceImplTest {

	@Autowired
	private ProfileServiceImpl sut;
	@Autowired
	private UserService userService;

	@Test
	void 프로필_생성_성공() throws IOException {
		// given
		SignUpRequest signUpRequest = new SignUpRequest(
			Email.of("test@email.com"),
			Password.of("passw0rd")
		);
		userService.signUp(signUpRequest);

		MockMultipartFile multipartFile = new MockMultipartFile(
			"test",
			"test.png",
			"image/png",
			new FileInputStream("/Users/hyeonjinlee/Documents/saeroksaerok/src/test/resources/images/test.png")
		);

		ProfileAddRequest request = new ProfileAddRequest(
			Email.of("test@email.com"),
			"nickname",
			multipartFile
		);

		// when
		ProfileAddResponse response = sut.addProfile(request);

		// then
		assertThat(response.getNickname()).isEqualTo("nickname");
		assertThat(response.getProfileImageUrl()).isNotNull();
	}
}
