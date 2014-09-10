/**
 * 
 */
package com.bbkmobile.iqoo.platform.base;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author wangbo
 * @version 1.0.0.0/2011-7-16
 */
public class BaseAction extends ActionSupport {

    protected HttpServletRequest getHttpServletRequest() {

        return ServletActionContext.getRequest();
    }

    protected HttpServletResponse getHttpServletResponse() {

        return ServletActionContext.getResponse();
    }

    protected HttpSession getHttpSession() {

        return getHttpServletRequest().getSession();
    }

    public void write(String content) throws Exception {

        Writer writer = null;
        try {
            HttpServletResponse response = getHttpServletResponse();
            response.setContentType("text/plain;charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setContentLength(content.getBytes("utf-8").length);

            ServletOutputStream out = response.getOutputStream();
            out.write(content.getBytes("utf-8"));

            out.flush();
            out.close();

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (null != writer) {
                    writer.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public void outwrite(String content, String contentType) throws Exception {

        ServletOutputStream out = null;
        try {
            HttpServletResponse response = getHttpServletResponse();
            response.setContentType(contentType);
            byte[] contentBytes = content.getBytes("utf-8");
            response.setContentLength(contentBytes.length);
            // response.setHeader("Cache-Control", "no-cache");

            out = response.getOutputStream();
            out.write(contentBytes);

            out.flush();
            out.close();

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    // protected UserInfo getUserVO(){
    //
    // return
    // (UserInfo)getHttpSession().getAttribute(Constants.SESSION_AUTH_USER);
    // }
    /**
	 * 
	 */
    private static final long serialVersionUID = 2310951881265325796L;

}
