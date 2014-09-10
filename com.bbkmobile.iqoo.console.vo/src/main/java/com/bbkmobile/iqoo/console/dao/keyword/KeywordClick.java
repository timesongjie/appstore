package com.bbkmobile.iqoo.console.dao.keyword;

import java.util.Date;

public class KeywordClick {

    private Long id;
    private Date click_date;
    private Keyword keyword;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Keyword getKeyword() {
        return this.keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }

    public Date getClick_date() {
        return this.click_date;
    }

    public void setClick_date(Date click_date) {
        this.click_date = click_date;
    }

}
