/**
 * 
 */
package com.bbkmobile.iqoo.common.util;

import org.apache.struts2.ServletActionContext;

/**
 * @author wangbo
 * @version 1.0.0.0/2011-7-19
 */
public class FilePathUtil {

    public static String getURL(String url) {

        String ul = ServletActionContext.getRequest().getContextPath() + url;
        ul = ul.replaceAll("/{2,}", "/");
        return ul;
    }
}
