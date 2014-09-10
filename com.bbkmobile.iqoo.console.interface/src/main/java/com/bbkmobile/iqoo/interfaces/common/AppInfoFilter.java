package com.bbkmobile.iqoo.interfaces.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.beans.BeanWrapperImpl;

import com.bbkmobile.iqoo.common.page.PageVO;
import com.bbkmobile.iqoo.common.vo.CommonResultAppInfo;
import com.bbkmobile.iqoo.console.dao.modelmgr.Model;

public class AppInfoFilter {

    @SuppressWarnings("rawtypes")
    public static List execute(Session session, final PageVO page, Model model,
            final SqlBuffer sqlBuffer) {
        final Class clazz = CommonResultAppInfo.class;
        return execute(session, page, model, clazz, sqlBuffer);
    }

    @SuppressWarnings("rawtypes")
    public static List execute(Session session, final PageVO page,
            final Model model, final Class clazz, final SqlBuffer sqlBuffer) {
        StringBuffer sql = new StringBuffer();

        sql.append(sqlBuffer.preSql());

        sql.append(appInfoSql);

        sql.append(sqlBuffer.postSql());

        sqlFilter(sql, model);// 机型过滤

        if (StringUtils.isNotEmpty(sqlBuffer.orderSq())) {
            sql.append(sqlBuffer.orderSq());
        }
        if (page != null) {
            sql.append(" limit :limit,:size ");
        }
        String temp = sql.toString();// idea 解析占位符 并替换成? 并获取对应index
        final Map<String, Integer> placeholder = SqlPlaceholderHandler
                .handleParams(temp);
        final String consSql = SqlPlaceholderHandler.handlePlaces(temp);
        final List result = new ArrayList();
        session.doWork(new Work() {
            @SuppressWarnings("unchecked")
            @Override
            public void execute(Connection connection) throws SQLException {
                PreparedStatement statement = connection
                        .prepareStatement(consSql.toString());
                queryAssign(model, statement, placeholder);// 机型过滤赋值
                if (page != null) {
                    statement.setObject(
                            placeholder.get("limit"),
                            (page.getCurrentPageNum() - 1)
                                    * page.getNumPerPage());
                    statement.setObject(placeholder.get("size"),
                            page.getNumPerPage());
                }
                sqlBuffer.queryAssign(statement, placeholder);// 子类扩展点
                ResultSet resultSet = statement.executeQuery();
                ResultSetMetaData metaData = null;
                BeanWrapperImpl beanWrapper = null;
                while (resultSet.next()) {
                    beanWrapper = new BeanWrapperImpl(clazz);
                    metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        String name = metaData.getColumnLabel(i);// metaData.getColumnName(i);
                        Object value = resultSet.getObject(i);
                        beanWrapper.setPropertyValue(name, value);
                    }
                    result.add(beanWrapper.getWrappedInstance());
                }
            }
        });
        return result;
    }

    private static final String appInfoSql = "  appinfo1_.appAuthor as developer, "
            + " appinfo1_.downloadCount as download_count, appinfo1_.appApk as download_url, appinfo1_.appIcon as icon_url, "
            + " appinfo1_.id as id, appinfo1_.official as offical, appinfo1_.appPackage as package_name, "
            + " appinfo1_.patchs as patchs, appinfo1_.commentCount as raters_count, appinfo1_.avgComment as score,"
            + " appinfo1_.apkSize as size, appinfo1_.tag as tag, appinfo1_.appCnName as title_zh,"
            + " appinfo1_.appEnName as title_en, appinfo1_.appVersion as version_name, appinfo1_.appVersionCode as version_code ";

    private static StringBuffer sqlFilter(StringBuffer sql, Model model) {
        if (model != null && model.getId() > 0) {
            if (model.getSdkVersion() != null) {
                sql.append(" and (appinfo1_.minSdkVersion<= :minSdkVersion or appinfo1_.minSdkVersion is null) ");
                sql.append(" and (appinfo1_.maxSdkVersion>= :maxSdkVersion or appinfo1_.maxSdkVersion is null)");
            }
            /*
             * if (StringUtils.isNotBlank(info.getCPU_ABI())) {
             * sql.append(" and (CPU_ABI is null or CPU_ABI like :CPU_ABI)"); }
             */
            sql.append(" and (appStatus=0 or (appStatus=13 and filter_model not like :filter_model )) ");
        } else {
            sql.append(" and (appStatus != 12) ");
        }
        return sql;
    }

    private static void queryAssign(Model model, PreparedStatement statement,
            Map<String, Integer> placeholder) throws SQLException {
        if (model != null && model.getId() > 0) {
            if (model.getSdkVersion() != null) {
                statement.setInt(placeholder.get("minSdkVersion"),
                        model.getSdkVersion());
                statement.setInt(placeholder.get("maxSdkVersion"),
                        model.getSdkVersion());
            }
            /*
             * if (StringUtils.isNotBlank(info.getCPU_ABI())) {
             * query.setString("CPU_ABI", transLike(info.getCPU_ABI())); }
             */statement.setString(placeholder.get("filter_model"),
                    transLike(model.getId()));
        }
    }

    private static String transLike(Object paramter) {
        return "%," + paramter + ",%";
    }
}
