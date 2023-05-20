package com.chorokchorok.saeroksaerok.core.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.chorokchorok.saeroksaerok.core.profile.domain.ProfileRepository;
import com.chorokchorok.saeroksaerok.core.user.domain.Email;
import com.chorokchorok.saeroksaerok.core.user.domain.Password;
import com.chorokchorok.saeroksaerok.core.user.domain.RefreshToken;
import com.chorokchorok.saeroksaerok.core.user.domain.RefreshTokenRepository;
import com.chorokchorok.saeroksaerok.core.user.domain.User;
import com.chorokchorok.saeroksaerok.core.user.domain.UserRepository;
import com.chorokchorok.saeroksaerok.core.user.dto.EmailDuplicatedCheckRequest;
import com.chorokchorok.saeroksaerok.core.user.dto.EmailDuplicatedCheckResponse;
import com.chorokchorok.saeroksaerok.core.user.dto.SignInRequest;
import com.chorokchorok.saeroksaerok.core.user.dto.SignInResponse;
import com.chorokchorok.saeroksaerok.core.user.dto.SignUpRequest;
import com.chorokchorok.saeroksaerok.core.user.dto.SignUpResponse;
import com.chorokchorok.saeroksaerok.testsupport.TestContainerTest;

@Transactional
@SpringBootTest
class UserServiceImplTest extends TestContainerTest {

	@Autowired
	private UserServiceImpl sut;
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProfileRepository profileRepository;

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

	@Test
	void 로그인_성공() {
		// given
		SignInRequest request = new SignInRequest(
			Email.of("test0519@email.com"),
			Password.of("passw0rd")
		);

		// when
		SignInResponse response = sut.signIn(request);

		RefreshToken refreshToken = refreshTokenRepository.findById(response.getRefreshToken()).get();
		Map<String, Object> claim = tokenService.parseClaim(response.getAccessToken());
		User user = userRepository.findByEmail(Email.of("test0519@email.com")).get();
		long profileId = profileRepository.findProfileIdByUserId(user.getId()).get();

		// then
		assertThat(response.getEmail().getValue()).isEqualTo("test0519@email.com");
		assertThat(response.getAccessToken()).isNotNull();
		assertThat(response.getRefreshToken()).isEqualTo(refreshToken.getRefreshToken());
	}
}
