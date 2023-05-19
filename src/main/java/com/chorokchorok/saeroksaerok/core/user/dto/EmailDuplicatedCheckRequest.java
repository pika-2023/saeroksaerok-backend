package com.chorokchorok.saeroksaerok.core.user.dto;

import com.chorokchorok.saeroksaerok.core.user.domain.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDuplicatedCheckRequest {

	private Email email;
}
