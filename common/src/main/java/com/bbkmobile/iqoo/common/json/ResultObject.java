package com.bbkmobile.iqoo.common.json;

/**
 * 统一输出对象格式
 * 
 * @author time
 * 
 */
public class ResultObject<T> {

    private Boolean result;
    private Integer maxPage;
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalCount;
    private T value;
    private String from;
    private Integer dbversion;
    private String info;
    private String comment;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(Integer maxPage) {
        this.maxPage = maxPage;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Integer getDbversion() {
        return dbversion;
    }

    public void setDbversion(Integer dbversion) {
        this.dbversion = dbversion;
    }

}
