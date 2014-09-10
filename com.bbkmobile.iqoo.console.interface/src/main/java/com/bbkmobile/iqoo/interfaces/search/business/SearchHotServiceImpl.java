package com.bbkmobile.iqoo.interfaces.search.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.console.dao.word.PopupWord;
import com.bbkmobile.iqoo.interfaces.word.dao.PopupWordDao;

@Service("iSearchHotService")
public class SearchHotServiceImpl implements SearchHotService {
    @Resource(name = "iPopupWordDao")
    private PopupWordDao popupWord;

    @Override
    public String getSearchHotWords(String model) throws Exception {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> searchHotWords = popupWord.getSearchHotWord(model);
            if (null != searchHotWords) {
                for (String searchHotWord : searchHotWords) {
                    sb.append(searchHotWord);
                    sb.append(",");
                }
                return sb.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String getPopupWords() throws Exception {
        try {
            StringBuilder sb = new StringBuilder();
            List<PopupWord> words = popupWord.list('1');
            if (null != words) {
                for (PopupWord word : words) {
                    sb.append(word.getWord());
                    sb.append(",");
                }
                return sb.toString();
            } else {
                return "";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String getSearchKeys() throws Exception {
        StringBuilder sb = new StringBuilder();
        try {
            List<PopupWord> words = popupWord.list('2');
            if (null != words) {
                for (PopupWord word : words) {
                    sb.append(word.getWord());
                    sb.append(",");
                }
            }
        } catch (Exception e) {
            throw e;
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 1);
        }
        return sb.toString();
    }

}
