package com.chorokchorok.saeroksaerok.configuration.naver;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "naver.cloud")
public class NaverProperties {

	private final String id;
	private final String secret;

	public NaverProperties(String id, String secret) {
		this.id = id;
		this.secret = secret;
	}
}
