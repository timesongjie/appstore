package com.bbkmobile.iqoo.interfaces.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public abstract class SqlBuffer {
    /**
     * select + 自需条件
     * 
     * @return
     */
    public abstract String preSql();

    /**
     * from + 关联表
     * 
     * @return
     */
    public abstract String postSql();

    public abstract String orderSq();

    /**
     * 自定义查询条件 赋值
     */
    public void queryAssign(PreparedStatement statement,
            Map<String, Integer> placeholder) throws SQLException {
    };
}
