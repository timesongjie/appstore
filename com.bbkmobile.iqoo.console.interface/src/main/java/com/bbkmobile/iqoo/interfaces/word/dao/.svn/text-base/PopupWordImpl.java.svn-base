package com.bbkmobile.iqoo.interfaces.word.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.console.dao.word.PopupWord;
import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;

@Repository("iPopupWordDao")
public class PopupWordImpl extends AnnotationBaseDao implements PopupWordDao {

	@Override
	public List<PopupWord> list(Character type) throws Exception {
		return getHibernateTemplate().find(" from PopupWord where type = ?",
				type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSearchHotWord(String model) throws Exception {
		try {
			String sqlString = "select word from t_search_hot_word order by show_order";
			List<?> list = getSession().createSQLQuery(sqlString).list();

			if (null != list && list.size() > 0) {
				return (List<String>) list;
			}
			return null;
		} catch (Exception e) {
			throw e;
		}
	}
}
