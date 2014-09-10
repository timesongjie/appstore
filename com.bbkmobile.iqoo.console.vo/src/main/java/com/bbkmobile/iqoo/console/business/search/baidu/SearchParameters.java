/**
 * 
 */
package com.bbkmobile.iqoo.console.business.search.baidu;

/**
 * word=愤怒&rn=20&pn=20&version=2.2.1&dpi=320_480
 * 
 * @author wangbo
 * 
 */
public class SearchParameters {

    private String word;

    /*
     * 每页显示结果数
     */
    private int recordNum;

    /*
     * 偏移量，即从第多少个开始
     */
    private int pageNum;

    private String dpi;
    private String version;

    private String id; // 榜单 分类

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(int recordNum) {
        this.recordNum = recordNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
