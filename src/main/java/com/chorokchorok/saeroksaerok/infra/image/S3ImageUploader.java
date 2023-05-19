package com.chorokchorok.saeroksaerok.infra.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.chorokchorok.saeroksaerok.configuration.s3.S3Properties;
import com.chorokchorok.saeroksaerok.core.profile.domain.Image;
import com.chorokchorok.saeroksaerok.core.profile.service.ImageUploader;
import com.chorokchorok.saeroksaerok.global.error.exception.BadRequestException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class S3ImageUploader implements ImageUploader {

	private final AmazonS3Client amazonS3Client;
	private final S3Properties properties;

	@Override
	public String upload(Image image) {
		File file = convert(image)
			.orElseThrow(() -> new BadRequestException("imageUpload-failed.uploading-file"));

		String filename = UUID.randomUUID().toString();
		String imageUrl = putS3(file, filename);
		deleteFile(file);
		return imageUrl;
	}

	private Optional<File> convert(Image image) {
		File file = new File("/tmp/" + image.getOriginalFilename());
		try {
			if (file.createNewFile()) {
				try (FileOutputStream fos = new FileOutputStream(file)) {
					fos.write(image.getInputStream().readAllBytes());
				}
			}
		} catch (IOException e) {
			throw new BadRequestException("imageUpload-failed.converting-file", e);
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
			throw new BadRequestException("imageUpload-failed.deleting-file", e);
		}
	}
}
