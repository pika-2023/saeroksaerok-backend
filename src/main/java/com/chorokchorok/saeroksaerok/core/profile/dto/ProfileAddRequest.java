package com.chorokchorok.saeroksaerok.core.profile.dto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.chorokchorok.saeroksaerok.core.profile.domain.Image;
import com.chorokchorok.saeroksaerok.core.user.domain.Email;
import com.chorokchorok.saeroksaerok.global.error.exception.BadRequestException;

import lombok.Data;

@Data
public class ProfileAddRequest {

	private Email email;
	private String nickname;
	private Image profileImage;

	public ProfileAddRequest(Email email, String nickname, MultipartFile profileImage) {
		this.email = email;
		this.nickname = nickname;
		this.profileImage = convertImage(profileImage);
	}

	public Image convertImage(MultipartFile profileImage) {
		if (profileImage == null) {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("defaultProfileImage.png");
			return new Image(inputStream, "defaultProfileImage.png");
		}
		InputStream inputStream = getInputStream(profileImage);
		String originalFilename = getOriginalFilename(profileImage);
		return new Image(inputStream, originalFilename);
	}

	private InputStream getInputStream(MultipartFile file) {
		ByteArrayInputStream byteArrayInputStream;
		try {
			byte[] byteArray = file.getBytes();
			byteArrayInputStream = new ByteArrayInputStream(byteArray);
		} catch (IOException e) {
			throw new BadRequestException("image-converting", e);
		}
		return byteArrayInputStream;
	}

	private String getOriginalFilename(MultipartFile file) {
		return file.getOriginalFilename();
	}
}
