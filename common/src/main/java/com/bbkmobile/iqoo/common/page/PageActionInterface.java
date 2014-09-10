/**
 * 
 */
package com.bbkmobile.iqoo.common.page;

/**
 * 对需要翻页的Action请实现该接口
 * 
 * @author wangbo
 * @version 1.0.0.0/2011-8-1
 */
public interface PageActionInterface {

    public void setPage(PageVO pageVO);

    public PageVO getPage();
}
