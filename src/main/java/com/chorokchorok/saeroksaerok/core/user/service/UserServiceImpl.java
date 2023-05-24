package com.chorokchorok.saeroksaerok.core.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chorokchorok.saeroksaerok.core.profile.domain.ProfileRepository;
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
import com.chorokchorok.saeroksaerok.global.error.exception.BadRequestException;
import com.chorokchorok.saeroksaerok.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final ProfileRepository profileRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final TokenService tokenService;

	@Transactional
	@Override
	public SignUpResponse signUp(SignUpRequest request) {
		// validate password
		if (!request.getPassword().getValue().equals(request.getCheckPassword().getValue())) {
			throw new BadRequestException("signIn-failed.password-missmatch");
		}

		// create user
		User user = new User(
			request.getEmail(),
			request.getPassword()
		);

		// save user
		User savedUser = userRepository.save(user);

		// create and return response
		return SignUpResponse.ofEntity(savedUser);
	}

	@Override
	public EmailDuplicatedCheckResponse checkEmailDuplicated(EmailDuplicatedCheckRequest request) {
		// check if duplicated
		boolean exists = userRepository.existsByEmail(request.getEmail());

		// create and return response
		return new EmailDuplicatedCheckResponse(exists);
	}

	@Transactional
	@Override
	public SignInResponse signIn(SignInRequest request) {
		// find user
		User user = userRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new NotFoundException("user", request.getEmail()));

		// validate password
		if (!request.getPassword().matches(user.getPassword())) {
			throw new BadRequestException("signIn-failed.password-missmatch");
		}

		// find profileId and create authentication tokens
		long profileId = profileRepository.findProfileIdByUserId(user.getId())
			.orElseThrow(() -> new NotFoundException("profile", user.getId()));

		String accessToken = tokenService.createAccessToken(user.getId(), profileId);
		String refreshToken = tokenService.createRefreshToken();

		// save refreshToken to redis
		saveRefreshToken(refreshToken, user.getId());

		// create and return response
		return new SignInResponse(user.getEmail(), accessToken, refreshToken);
	}

	private void saveRefreshToken(String refreshTokenStr, long userId) {
		RefreshToken refreshToken = new RefreshToken(refreshTokenStr, userId);
		refreshTokenRepository.save(refreshToken);
	}

	private void deleteRefreshToken(String refreshTokenStr) {
		refreshTokenRepository.deleteById(refreshTokenStr);
	}
}
