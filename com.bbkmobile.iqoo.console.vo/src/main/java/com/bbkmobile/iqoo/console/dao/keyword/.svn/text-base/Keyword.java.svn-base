package com.bbkmobile.iqoo.console.dao.keyword;

import java.util.Date;

public class Keyword implements java.io.Serializable{
    
	private static final long serialVersionUID = -1535100742318988405L;
	
    private Long id; 
    private String keyword;           //关键词
    private Integer sequence;          //序号
    private Character recommend;      //是否推荐 1：推荐  0：不推荐
    //出现频率定级为当前词设定的出现频率，用于搜索词过多无法一次显示时的所采用的动态效果提供参考。
    //目前建议值：1、2、3, 3级表示频率最高。
    private Integer rate_rank;        //出现频率定级 
    private Date add_date;            //添加时期
    private Date modify_date;         //修改日期
    
    private Date from_date;     //起止时间（查询时开始时间）
    private Date to_date;       //提交时间（查询时结束时间）
    
    private Integer search_num;       //搜索次数
    private Integer click_num;        //点击次数
    
    public Date getFrom_date() {
        return this.from_date;
    }
    public void setFrom_date(Date from_date) {
        this.from_date = from_date;
    }
    public Date getTo_date() {
        return this.to_date;
    }
    public void setTo_date(Date to_date) {
        this.to_date = to_date;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getKeyword() {
        return this.keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public Integer getSequence() {
        return this.sequence;
    }
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
    public Character getRecommend() {
        return this.recommend;
    }
    public void setRecommend(Character recommend) {
        this.recommend = recommend;
    }
    public Integer getSearch_num() {
        return this.search_num;
    }
    public void setSearch_num(Integer search_num) {
        this.search_num = search_num;
    }
    public Integer getClick_num() {
        return this.click_num;
    }
    public void setClick_num(Integer click_num) {
        this.click_num = click_num;
    }
    public Integer getRate_rank() {
        return this.rate_rank;
    }
    public void setRate_rank(Integer rate_rank) {
        this.rate_rank = rate_rank;
    }
    public Date getAdd_date() {
        return this.add_date;
    }
    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }
    public Date getModify_date() {
        return this.modify_date;
    }
    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    } 
}
