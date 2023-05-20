package com.chorokchorok.saeroksaerok.configuration.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

	// access token validity in millis
	private final int accessValidity;

	// refresh token validity in millis
	private final int refreshValidity;

	private final String secret;
}
