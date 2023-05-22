package com.chorokchorok.saeroksaerok.infra.speechRecognition;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chorokchorok.saeroksaerok.core.common.service.SpeechRecognitionService;
import com.chorokchorok.saeroksaerok.global.error.exception.BadRequestException;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GcpSpeechRecognitionService implements SpeechRecognitionService {

	@Override
	public String transcribeAudioToText(MultipartFile audio) {
		//
		String transcript = "";

		//
		try {
			SpeechClient speechClient = SpeechClient.create();

			byte[] bytes = audio.getInputStream().readAllBytes();
			ByteString content = ByteString.copyFrom(bytes);

			RecognitionConfig recognitionConfig = RecognitionConfig.newBuilder()
				.setEncoding(RecognitionConfig.AudioEncoding.ENCODING_UNSPECIFIED)
				.setSampleRateHertz(16000)
				.setLanguageCode("ko-KR")
				.build();

			RecognitionAudio recognitionAudio = RecognitionAudio.newBuilder()
				.setContent(content)
				.build();

			RecognizeResponse response = speechClient.recognize(recognitionConfig, recognitionAudio);
			List<SpeechRecognitionResult> results = response.getResultsList();

			for (SpeechRecognitionResult result : results) {
				SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
				transcript += alternative.getTranscript();
			}

			speechClient.close();

		} catch (IOException e) {
			e.printStackTrace();
			throw new BadRequestException("audioTranscription-failed.file-transcription");
		}

		//
		return transcript;
	}
}
