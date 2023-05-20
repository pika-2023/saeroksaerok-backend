package com.chorokchorok.saeroksaerok.configuration.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Getter;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "cloud.aws.s3")
public class S3Properties {

	private final String bucket;

	public S3Properties(String bucket) {
		this.bucket = bucket;
	}
}
