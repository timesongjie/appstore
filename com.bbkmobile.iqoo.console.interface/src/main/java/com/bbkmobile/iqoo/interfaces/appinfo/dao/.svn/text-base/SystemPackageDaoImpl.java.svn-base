package com.bbkmobile.iqoo.interfaces.appinfo.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;
@Repository("systemPackageDao")
public class SystemPackageDaoImpl extends AnnotationBaseDao implements SystemPackageDao{

	@Override
	public List getSystemPackage() throws Exception {
		try {
			String queryString = "select systemPackage from SystemPackage ";
			Session session = getSession();
			Query queryObject = session.createQuery(queryString);
			return queryObject.list();
		} catch (Exception e) {
			Lg.error(LgType.APPINFO, "判断是否为系统包名时出错，error=" + e.getMessage());
			throw e;
		}
	}
}
