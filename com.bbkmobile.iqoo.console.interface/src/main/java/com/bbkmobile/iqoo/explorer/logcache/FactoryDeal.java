/**
 * 浏览器链接点击，日志处理接口工厂
 */
package com.bbkmobile.iqoo.explorer.logcache;

/**
 * @author wangbo
 * 
 */
public class FactoryDeal {

    public static Deal createDeal() {
        return new DealPrint();
    }
}
