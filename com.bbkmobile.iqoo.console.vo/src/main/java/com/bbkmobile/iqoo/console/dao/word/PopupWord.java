package com.bbkmobile.iqoo.console.dao.word;

import java.util.Date;

import com.bbkmobile.iqoo.platform.annotation.field.FieldConfig;
import com.bbkmobile.iqoo.platform.annotation.field.type.MatchModeType;

/**
 * 手机端 轮播热词 和 搜索热词
 * 
 * @author time
 * 
 */
public class PopupWord {

    private Long id;
    @FieldConfig(criterionMatchMode = MatchModeType.EXACT)
    private String word;
    private Date createDate;
    @FieldConfig(criterionMatchMode = MatchModeType.EXACT)
    private Character type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Character getType() {
        return type;
    }

    public void setType(Character type) {
        this.type = type;
    }
}
