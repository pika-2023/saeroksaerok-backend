package com.chorokchorok.saeroksaerok.infra.speechRecognition;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.chorokchorok.saeroksaerok.configuration.dalle.DalleProperties;
import com.chorokchorok.saeroksaerok.core.common.service.SpeechRecognitionService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OpenAiSpeechRecognitionService implements SpeechRecognitionService {

	private final DalleProperties properties;

	@Override
	public String transcribeAudioToText(MultipartFile audio) {
		RestTemplate template = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("Authorization", "Bearer " + properties.getApiKey());

		try {
			MultiValueMap<Object, Object> body = new LinkedMultiValueMap<>();
			ByteArrayResource fileResource = new ByteArrayResource(audio.getBytes()) {
				@Override
				public String getFilename() {
					return audio.getOriginalFilename();
				}
			};

			body.add("file", fileResource);
			body.add("model", "whisper-1");

			HttpEntity<MultiValueMap<Object, Object>> request = new HttpEntity<>(body, headers);
			ResponseEntity<String> response = template.postForEntity(
				"https://api.openai.com/v1/audio/transcriptions", request, String.class
			);

			return response.getBody().split("\"")[3];

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
