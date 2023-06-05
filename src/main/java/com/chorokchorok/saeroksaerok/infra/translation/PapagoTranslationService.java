package com.chorokchorok.saeroksaerok.infra.translation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chorokchorok.saeroksaerok.configuration.papago.PapagoProperties;
import com.chorokchorok.saeroksaerok.core.diary.service.TranslationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PapagoTranslationService implements TranslationService {

	private final PapagoProperties properties;

	@Override
	public String translate(String korean) {
		RestTemplate template = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Naver-Client-Id", properties.getId());
		headers.set("X-Naver-Client-Secret", properties.getSecret());

		Map<String, String> body = new HashMap<>();
		body.put("source", "ko");
		body.put("target", "en");
		body.put("text", korean);

		HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
		ResponseEntity<Object> response = template.postForEntity(
			properties.getUrl(), request, Object.class
		);

		String split = response.getBody().toString().split("translatedText=")[1];
		return split.split(", engineType=")[0];
	}
}
