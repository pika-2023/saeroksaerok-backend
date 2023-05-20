package com.chorokchorok.saeroksaerok.core.user.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(Email email);

	Optional<User> findByEmail(Email email);
}
