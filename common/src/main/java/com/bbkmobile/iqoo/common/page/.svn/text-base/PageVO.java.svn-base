/**
 * 
 */
package com.bbkmobile.iqoo.common.page;

/**
 * @author wangbo
 * @version 1.0.0.0/2011-7-28
 */
public class PageVO {

	
	private Integer pageCount; // 页数
	private Integer recordCount; // 记录数
	private Integer currentPageNum=1; // 当前页码
	private Integer numPerPage = 10; // 每页的记录数
	/**
	 * @return the pageCount
	 */
	public Integer getPageCount() {
		return pageCount;
	}
	/**
	 * @param pageCount the pageCount to set
	 */
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	/**
	 * @return the recordCount
	 */
	public Integer getRecordCount() {
		return recordCount;
	}
	/**
	 * @param recordCount the recordCount to set
	 */
	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
		setPageCount((recordCount+getNumPerPage()-1)/getNumPerPage());
		
	}
	/**
	 * @return the currentPageNum
	 */
	public Integer getCurrentPageNum() {
		return currentPageNum;
	}
	/**
	 * @param currentPageNum the currentPageNum to set
	 */
	public void setCurrentPageNum(Integer currentPageNum) {
		this.currentPageNum = currentPageNum;
	}
	/**
	 * @return the numPerPage
	 */
	public Integer getNumPerPage() {
		return numPerPage;
	}
	/**
	 * @param numPerPage the numPerPage to set
	 */
	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}
	
	
}
