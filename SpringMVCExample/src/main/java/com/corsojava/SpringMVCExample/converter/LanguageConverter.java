package com.corsojava.SpringMVCExample.converter;

import javax.annotation.Resource;

import org.springframework.core.convert.converter.Converter;

import com.corsojava.sakilajpa.dao.LanguageDao;
import com.corsojava.sakilajpa.model.Language;

public class LanguageConverter implements Converter<String, Language> {

	@Resource
	private LanguageDao languageDao;

	@Override
	public Language convert(String arg0) {
		return languageDao.getLanguage(Short.valueOf(arg0));
	}

}
