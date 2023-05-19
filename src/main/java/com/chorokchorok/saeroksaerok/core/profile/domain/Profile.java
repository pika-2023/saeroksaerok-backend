package com.chorokchorok.saeroksaerok.core.profile.domain;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import javax.persistence.Column;
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
public class Profile extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(updatable = false)
	private Long id;

}
