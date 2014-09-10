package com.bbkmobile.iqoo.interfaces.search.business;

public interface SearchHotService {

    String getSearchKeys() throws Exception;

    String getPopupWords() throws Exception;

    String getSearchHotWords(String model) throws Exception;

}
