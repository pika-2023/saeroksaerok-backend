package com.chorokchorok.saeroksaerok.infra.audio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.chorokchorok.saeroksaerok.configuration.s3.S3Properties;
import com.chorokchorok.saeroksaerok.core.reply.service.AudioUploader;
import com.chorokchorok.saeroksaerok.global.error.exception.BadRequestException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class S3AudioUploader implements AudioUploader {

	private final AmazonS3Client amazonS3Client;
	private final S3Properties properties;

	@Override
	public String upload(MultipartFile audio) {
		File file = convert(audio)
			.orElseThrow(() -> new BadRequestException("imageUpload-failed.uploading-file"));

		String filename = UUID.randomUUID().toString();
		String audioUrl = putS3(file, filename);
		deleteFile(file);
		return audioUrl;
	}

	private Optional<File> convert(MultipartFile audio) {
		File file = new File("/tmp/" + audio.getOriginalFilename());
		try {
			if (file.createNewFile()) {
				try (FileOutputStream fos = new FileOutputStream(file)) {
					fos.write(audio.getInputStream().readAllBytes());
				}
			}
		} catch (IOException e) {
			throw new BadRequestException("audioUpload-failed.converting-file", e);
		}
		return Optional.of(file);
	}

	private String putS3(File file, String filename) {
		amazonS3Client.putObject(
			new PutObjectRequest(properties.getBucket(), filename, file).withCannedAcl(
				CannedAccessControlList.PublicRead
			)
		);
		return amazonS3Client.getUrl(properties.getBucket(), filename).toString();
	}

	private void deleteFile(File file) {
		try {
			file.delete();
		} catch (IllegalArgumentException e) {
			throw new BadRequestException("audioUpload-failed.deleting-file", e);
		}
	}
}
