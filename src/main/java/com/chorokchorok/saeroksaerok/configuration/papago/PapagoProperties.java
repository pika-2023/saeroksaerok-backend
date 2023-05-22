package com.chorokchorok.saeroksaerok.configuration.papago;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "papago.client")
public class PapagoProperties {

	private final String id;
	private final String secret;
	private final String url;

	public PapagoProperties(String id, String secret, String url) {
		this.id = id;
		this.secret = secret;
		this.url = url;
	}
}
