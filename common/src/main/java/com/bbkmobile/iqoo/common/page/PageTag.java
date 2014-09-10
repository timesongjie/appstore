/**
 * 
 */
package com.bbkmobile.iqoo.common.page;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author wangbo
 * @version 1.0.0.0/2011-8-1
 */
public class PageTag extends TagSupport {

    private final Log log = LogFactory.getLog(PageTag.class);

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException {
        // TODO Auto-generated method stub
        try {
            // 第1/31页 共302条
            setPageVO((PageVO) pageContext.getRequest().getAttribute("page"));

            StringBuilder tagContent = new StringBuilder();
            tagContent.append("第 <font color=\"red\">");
            tagContent.append(getPageVO().getCurrentPageNum());
            tagContent.append("</font>/");
            tagContent.append(getPageVO().getPageCount());
            tagContent.append("页  共");
            tagContent.append(getPageVO().getRecordCount());
            tagContent.append("条  ");
            tagContent
                    .append("<select name=\"turnPage\" id=\"turnPage\" class=\"txt2\" onchange=\"changePage('"
                            + getFormID() + "');\">");
            for (int i = 1; i <= getPageVO().getPageCount(); i++) {
                tagContent.append("<option value=\"");
                tagContent.append(i);

                if (i == getPageVO().getCurrentPageNum()) {
                    tagContent.append("\" selected=\"selected\">");
                } else {
                    tagContent.append("\">");
                }
                tagContent.append(i);
                tagContent.append("</option>");
            }
            tagContent.append("</select>");

            if (getPageVO().getCurrentPageNum() > 1) {
                tagContent
                        .append("<a href=\"javascript:firstPage('"
                                + getFormID()
                                + "');\"> &nbsp;&nbsp;首页</a>&nbsp;&nbsp;");
                tagContent.append("<a href=\"javascript:prePage('"
                        + getFormID() + "');\"> 上一页</a>&nbsp;&nbsp;");
            }
            if (getPageVO().getCurrentPageNum() < getPageVO().getPageCount()) {
                tagContent.append("<a href=\"javascript:nextPage('"
                        + getFormID() + "');\">下一页</a>&nbsp;&nbsp;");
                tagContent.append("<a href=\"javascript:lastPage('"
                        + getFormID() + "');\">末页</a>");
            }

            tagContent
                    .append("<input type=\"hidden\" name=\"page.currentPageNum\" id=\"page_currentPageNum\" value=\""
                            + getPageVO().getCurrentPageNum() + "\"/> ");
            tagContent
                    .append("<input type=\"hidden\" name=\"page.pageCount\" id=\"page_pageCount\" value=\""
                            + getPageVO().getPageCount() + "\"/> ");
            // zuoshengdong add
            tagContent
                    .append("<input type=\"hidden\" name=\"page.recordCount\" id=\"page_recordCount\" value=\""
                            + getPageVO().getRecordCount() + "\"/> ");

            pageContext.getOut().write(tagContent.toString());
        } catch (IOException e) {
            log.error("翻页标签解析发生异常：", e);
        }
        return EVAL_PAGE;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the formID
     */
    public String getFormID() {
        return formID;
    }

    /**
     * @param formID
     *            the formID to set
     */
    public void setFormID(String formID) {
        this.formID = formID;
    }

    /**
     * @return the pageVO
     */
    private PageVO getPageVO() {
        return pageVO;
    }

    /**
     * @param pageVO
     *            the pageVO to set
     */
    private void setPageVO(PageVO pageVO) {
        this.pageVO = pageVO;
    }

    private String name;

    // 对应的Form的ID
    private String formID;

    private PageVO pageVO;

    /**
	 * 
	 */
    private static final long serialVersionUID = 3800432936996690839L;

}
