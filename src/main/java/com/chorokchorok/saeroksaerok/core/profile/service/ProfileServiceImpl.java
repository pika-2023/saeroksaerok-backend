package com.chorokchorok.saeroksaerok.core.profile.service;

import org.springframework.stereotype.Service;

import com.chorokchorok.saeroksaerok.core.profile.domain.Profile;
import com.chorokchorok.saeroksaerok.core.profile.domain.ProfileRepository;
import com.chorokchorok.saeroksaerok.core.profile.dto.ProfileAddRequest;
import com.chorokchorok.saeroksaerok.core.profile.dto.ProfileAddResponse;
import com.chorokchorok.saeroksaerok.core.user.domain.User;
import com.chorokchorok.saeroksaerok.core.user.domain.UserRepository;
import com.chorokchorok.saeroksaerok.global.error.exception.BadRequestException;
import com.chorokchorok.saeroksaerok.global.error.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

	private final ProfileRepository profileRepository;
	private final UserRepository userRepository;
	private final ImageUploader imageUploader;

	@Override
	public ProfileAddResponse addProfile(ProfileAddRequest request) {
		// find user
		User user = userRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new NotFoundException("user", request.getEmail().getValue()));

		// check if duplicate
		boolean exists = profileRepository.existsByUser(user);
		if (exists) {
			throw new BadRequestException("profileAdd-failed.already-exists");
		}

		// upload image
		String imageUrl = request.getProfileImage().uploadBy(imageUploader);

		// create profile
		Profile profile = new Profile(user, request.getNickname(), imageUrl);

		// save profile
		Profile savedProfile = profileRepository.save(profile);

		// create and return response
		return ProfileAddResponse.ofEntity(savedProfile);
	}
}
