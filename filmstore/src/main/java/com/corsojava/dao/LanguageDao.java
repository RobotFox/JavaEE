package com.corsojava.dao;

import com.corsojava.model.Language;

public interface LanguageDao {

	Language getLanguage(int id);

	int addLanguage(Language language);

}
