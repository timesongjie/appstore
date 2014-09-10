/**
 * 处理浏览器链接点击日志的接口
 */
package com.bbkmobile.iqoo.interfaces.common.log;

import java.util.List;

import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;

/**
 * @author wangbo
 * 
 */
public abstract class Deal extends AnnotationBaseDao {

    public abstract void deal(List<String[]> ls);
}
