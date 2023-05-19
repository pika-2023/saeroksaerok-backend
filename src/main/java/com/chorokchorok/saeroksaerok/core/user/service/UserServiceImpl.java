package com.chorokchorok.saeroksaerok.core.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chorokchorok.saeroksaerok.core.user.domain.User;
import com.chorokchorok.saeroksaerok.core.user.domain.UserRepository;
import com.chorokchorok.saeroksaerok.core.user.dto.EmailDuplicatedCheckRequest;
import com.chorokchorok.saeroksaerok.core.user.dto.EmailDuplicatedCheckResponse;
import com.chorokchorok.saeroksaerok.core.user.dto.SignUpRequest;
import com.chorokchorok.saeroksaerok.core.user.dto.SignUpResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Transactional
	@Override
	public SignUpResponse signUp(SignUpRequest request) {
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
}
