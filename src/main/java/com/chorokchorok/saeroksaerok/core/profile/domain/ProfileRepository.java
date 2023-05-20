package com.chorokchorok.saeroksaerok.core.profile.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chorokchorok.saeroksaerok.core.user.domain.User;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
	boolean existsByUser(User user);

	@Query("SELECT p.id "
		+ "FROM Profile p "
		+ "WHERE p.user.id = :userId")
	Optional<Long> findProfileIdByUserId(@Param("userId") long userId);
}
