package com.bbkmobile.iqoo.explorer.logcache;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.interfaces.appinfo.dao.AppInfoDAO;

/**
 * @author wangbo
 * 
 */
public class DealPrint implements ApplicationContextAware, Deal {

    private static ApplicationContext context;// 声明一个静态变量保存

    @Override
    public void Deal(List<String> logs) {
        try {
            AppInfoDAO appInfoDAO = (AppInfoDAO) context.getBean("iAppInfoDAO");
            appInfoDAO.saveLogs(logs);
        } catch (Exception e) {
            e.printStackTrace();
            Lg.error(LgType.APPINFO, "批量插入日志数据时异常：" + e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        DealPrint.context = context;
    }
}
