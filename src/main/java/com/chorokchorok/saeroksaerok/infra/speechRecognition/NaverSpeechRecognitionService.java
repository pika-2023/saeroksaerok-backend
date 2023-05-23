package com.chorokchorok.saeroksaerok.infra.speechRecognition;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chorokchorok.saeroksaerok.configuration.naver.NaverProperties;
import com.chorokchorok.saeroksaerok.global.error.exception.BadRequestException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class NaverSpeechRecognitionService {

	private final NaverProperties properties;

	// @Override
	// public String transcribeAudioToText(MultipartFile audio) {
	// 	RestTemplate template = new RestTemplate();
	//
	// 	HttpHeaders headers = new HttpHeaders();
	// 	headers.set("X-NCP-APIGW-API-KEY-ID", properties.getId());
	// 	headers.set("X-NCP-APIGW-API-KEY", properties.getSecret());
	// 	headers.set("Content-Type", "application/octet-stream");
	//
	// 	HashMap<String, String> body = new HashMap<>();
	// 	body.put("image", "");
	//
	// 	return null;
	// }

	// @Override
	public String transcribeAudioToText(MultipartFile audio) {
		try {
			String language = "Kor";
			String apiUrl = "https://naveropenapi.apigw-pub.fin-ntruss.com/recog/v1/stt?lang=" + language;
			URL url = new URL(apiUrl);

			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			connection.setRequestProperty("X-NCP-APIGW-API-KEY-ID", properties.getId());
			connection.setRequestProperty("X-NCP-APIGW-API-KEY", properties.getSecret());

			OutputStream outputStream = connection.getOutputStream();
			InputStream inputStream = audio.getInputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.flush();
			outputStream.close();

			BufferedReader reader = null;
			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			} else {
				log.error("response code = " + responseCode);
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			}

			String inputLine;
			if (reader != null) {
				StringBuffer response = new StringBuffer();
				while ((inputLine = reader.readLine()) != null) {
					response.append(inputLine);
				}
				reader.close();
				return response.toString();
			} else {
				return "error";
			}
		} catch (Exception e) {
			throw new BadRequestException("audioTranscription-failed.sending-api", e);
		}
	}
}
