package com.chorokchorok.saeroksaerok.configuration.vito;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "vito")
public class VitoProperties {

	private final String id;
	private final String secret;

	public VitoProperties(String id, String secret) {
		this.id = id;
		this.secret = secret;
	}
}
