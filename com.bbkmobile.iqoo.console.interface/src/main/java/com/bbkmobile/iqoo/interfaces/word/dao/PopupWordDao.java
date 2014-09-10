package com.bbkmobile.iqoo.interfaces.word.dao;

import java.util.List;

import com.bbkmobile.iqoo.console.dao.word.PopupWord;

/**
 * 
 * @author time
 * 
 */
public interface PopupWordDao {

    public List<PopupWord> list(Character type) throws Exception;

    List<String> getSearchHotWord(String model) throws Exception;
}
