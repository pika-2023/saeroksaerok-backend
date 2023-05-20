package com.chorokchorok.saeroksaerok.core.profile.service;

import com.chorokchorok.saeroksaerok.core.profile.dto.ProfileAddRequest;
import com.chorokchorok.saeroksaerok.core.profile.dto.ProfileAddResponse;

public interface ProfileService {

	ProfileAddResponse addProfile(ProfileAddRequest request);
}
