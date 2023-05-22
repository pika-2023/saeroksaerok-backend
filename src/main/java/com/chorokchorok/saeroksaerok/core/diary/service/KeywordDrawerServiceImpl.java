package com.chorokchorok.saeroksaerok.core.diary.service;

import org.springframework.stereotype.Service;

@Service
public class KeywordDrawerServiceImpl implements KeywordDrawerService {

	private String[] keywords = {"설레임", "그리움"};

	@Override
	public String keywordDraw() {
		double random = Math.random();
		int index = (int)Math.round(random * (keywords.length - 1));
		return keywords[index];
	}
}
