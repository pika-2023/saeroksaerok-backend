package com.chorokchorok.saeroksaerok.core.profile.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chorokchorok.saeroksaerok.core.user.domain.User;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
	boolean existsByUser(User user);
}
