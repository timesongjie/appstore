package com.bbkmobile.iqoo.interfaces.common;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
/**
 * 注解下所有dao都继承该类
 * @author time
 *
 */
public class AnnotationBaseDao extends HibernateDaoSupport{
	@Autowired
	public void init(SessionFactory factory) {
		setSessionFactory(factory);
	}
}
