package com.bbkmobile.iqoo.interfaces.search.business;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.interfaces.search.dao.AppSearchWordDao;
import com.bbkmobile.iqoo.interfaces.search.vo.HotAppsSearch;

@Service("iAppSearchKeyService")
public class AppSearchKeyServiceImpl implements AppSearchKeyService{
	@Resource(name="iAppSearchWordDao")
	private AppSearchWordDao appSearchWordDao;
	
	@Override
	public List<HotAppsSearch> list() throws Exception {
		return appSearchWordDao.list();
	}
}
