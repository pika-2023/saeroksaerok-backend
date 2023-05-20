package com.chorokchorok.saeroksaerok.core.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDuplicatedCheckResponse {

	private boolean duplicated;
}
