package com.chorokchorok.saeroksaerok.core.common.service;

import org.springframework.web.multipart.MultipartFile;

public interface SpeechRecognitionService {

	String transcribeAudioToText(MultipartFile audio);
}
