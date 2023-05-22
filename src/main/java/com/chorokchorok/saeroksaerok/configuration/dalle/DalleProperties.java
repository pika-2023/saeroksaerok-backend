package com.chorokchorok.saeroksaerok.configuration.dalle;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "openai")
public class DalleProperties {

	private final String apiKey;
	private final String model;
	private final String url;

	public DalleProperties(String apiKey, String model, String url) {
		this.apiKey = apiKey;
		this.model = model;
		this.url = url;
	}
}
