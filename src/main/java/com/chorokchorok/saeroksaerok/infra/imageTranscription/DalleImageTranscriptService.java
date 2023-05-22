package com.chorokchorok.saeroksaerok.infra.imageTranscription;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chorokchorok.saeroksaerok.configuration.dalle.DalleProperties;
import com.chorokchorok.saeroksaerok.core.diary.service.ImageTranscriptService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DalleImageTranscriptService implements ImageTranscriptService {

	private final DalleProperties properties;

	@Override
	public String transcibe(String prompt) {
		RestTemplate template = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + properties.getApiKey());

		Map<String, Object> body = new HashMap<>();
		body.put("model", properties.getModel());
		body.put("prompt", prompt);
		body.put("num_images", 1);
		body.put("response_format", "url");

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
		ResponseEntity<Object> response = template.postForEntity(
			properties.getUrl(), request, Object.class
		);

		String splits = response.getBody().toString().split("url=")[1];
		return splits.split("}")[0];
	}
}
