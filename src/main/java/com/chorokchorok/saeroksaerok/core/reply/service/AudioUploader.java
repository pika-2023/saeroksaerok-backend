package com.chorokchorok.saeroksaerok.core.reply.service;

import org.springframework.web.multipart.MultipartFile;

public interface AudioUploader {
	String upload(MultipartFile audio);
}
