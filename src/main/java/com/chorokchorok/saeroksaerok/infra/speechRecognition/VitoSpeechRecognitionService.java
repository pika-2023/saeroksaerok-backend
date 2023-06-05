package com.chorokchorok.saeroksaerok.infra.speechRecognition;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.chorokchorok.saeroksaerok.configuration.vito.VitoProperties;
import com.chorokchorok.saeroksaerok.core.common.service.SpeechRecognitionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VitoSpeechRecognitionService implements SpeechRecognitionService {

	private final VitoProperties properties;

	@Override
	public String transcribeAudioToText(MultipartFile audio) {
		String accessToken = getAccessToken();
		String transcribeId = getTranscribeId(audio, accessToken);

		try {
			for (int second = 0; second < 15; second++) {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		try {
			URL url = new URL("https://openapi.vito.ai/v1/transcribe/" + transcribeId);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("accept", "application/json");
			connection.setRequestProperty("Authorization", "Bearer " + accessToken);

			InputStream responseStream =
				connection.getResponseCode() / 100 == 2 ? connection.getInputStream() : connection.getErrorStream();
			Scanner scanner = new Scanner(responseStream).useDelimiter("\\A");
			String response = scanner.hasNext() ? scanner.next() : "";
			scanner.close();

			return response.split("msg\":\"")[1].split("\"}")[0];

		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String getTranscribeId(MultipartFile audio, String accessToken) {
		try {
			URL url = new URL("https://openapi.vito.ai/v1/transcribe");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("accept", "application/json");
			connection.setRequestProperty("Authorization", "Bearer " + accessToken);
			connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=authsample");
			connection.setDoOutput(true);

			DataOutputStream outputStream;
			outputStream = new DataOutputStream(connection.getOutputStream());
			outputStream.writeBytes("--authsample\r\n");
			outputStream.writeBytes(
				"Content-Disposition: form-data; name=\"file\";filename=\"" + audio.getOriginalFilename() + "\"\r\n");
			outputStream.writeBytes(
				"Content-Type: " + URLConnection.guessContentTypeFromName(audio.getOriginalFilename()) + "\r\n");
			outputStream.writeBytes("Content-Transfer-Encoding: binary" + "\r\n");
			outputStream.writeBytes("\r\n");

			InputStream inputStream = audio.getInputStream();
			byte[] buffer = new byte[(int)audio.getSize()];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
				outputStream.writeBytes("\r\n");
				outputStream.writeBytes("--authsample\r\n");
			}
			outputStream.writeBytes("\r\n");
			outputStream.writeBytes("--authsample\r\n");
			outputStream.writeBytes("Content-Disposition: form-data; name=\"config\"\r\n");
			outputStream.writeBytes("Content-Type: application/json\r\n");
			outputStream.writeBytes("\r\n");
			outputStream.writeBytes("{\n  \"diarization\": {\n");
			outputStream.writeBytes("	\"use_verification\": false\n");
			outputStream.writeBytes("	},\n");
			outputStream.writeBytes("\"use_multi_channel\": false,\n");
			outputStream.writeBytes("\"use_itn\": false,\n");
			outputStream.writeBytes("\"use_disfluency_filter\": false,\n");
			outputStream.writeBytes("\"use_profanity_filter\": false,\n");
			outputStream.writeBytes("\"paragraph_splitter\": {\n");
			outputStream.writeBytes("	\"min\": 10,\n");
			outputStream.writeBytes("	\"max\": 50\n");
			outputStream.writeBytes("	}\n");
			outputStream.writeBytes("}");
			outputStream.writeBytes("\r\n");
			outputStream.writeBytes("--authsample\r\n");
			outputStream.flush();
			outputStream.close();

			InputStream responseStream =
				connection.getResponseCode() / 100 == 2 ? connection.getInputStream() : connection.getErrorStream();
			Scanner scanner = new Scanner(responseStream).useDelimiter("\\A");
			String response = scanner.hasNext() ? scanner.next() : "";
			scanner.close();

			return response.split("\"")[3];

		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String getAccessToken() {
		RestTemplate template = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/x-www-form-urlencoded");
		String body = "client_id=" + properties.getId() + "&client_secret=" + properties.getSecret();

		HttpEntity<String> request = new HttpEntity<>(body, headers);
		ResponseEntity<Object> response = template.postForEntity(
			"https://openapi.vito.ai/v1/authenticate", request, Object.class
		);

		return response.getBody().toString().split("=")[1].split(",")[0];
	}
}
