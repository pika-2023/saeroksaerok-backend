package com.chorokchorok.saeroksaerok.core.user.domain;

import static com.chorokchorok.saeroksaerok.global.util.Preconditions.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.chorokchorok.saeroksaerok.core.common.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(updatable = false)
	private Long id;

	@Embedded
	private Email email;

	@Embedded
	private Password password;

	public User(Email email, Password password) {
		checkNotNull(email, "User.email cannot be null");
		checkNotNull(password, "User.password cannot be null");
		this.email = email;
		this.password = password;
	}
}
