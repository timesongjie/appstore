/**
 * 
 */
package com.bbkmobile.iqoo.platform.base;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author wangbo
 * @version 1.0.0.0/2011-7-19
 */
public class BaseStreamAction extends BaseAction {

    protected final static Log log = LogFactory.getLog(BaseStreamAction.class);

    public void write(String content) throws IOException, Exception {

        Writer writer = null;
        try {
            getHttpServletResponse().setContentType("text/html;charset=UTF-8");
            writer = getHttpServletResponse().getWriter();
            writer.write(content);

        } catch (IOException e) {
            log.error(e);
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            try {
                if (null != writer) {
                    writer.close();
                }
            } catch (IOException e) {
                log.info(e);
            }

        }
    }

    public void write(String content, String contentType) throws IOException,
            Exception {

        Writer writer = null;
        try {
            getHttpServletResponse().setContentType(contentType);
            // getHttpServletResponse().setCharacterEncoding(charset);
            writer = getHttpServletResponse().getWriter();
            writer.write(content);

        } catch (IOException e) {
            log.error(e);
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            try {
                if (null != writer) {
                    writer.close();
                }
            } catch (IOException e) {
                log.info(e);
            }

        }
    }

    public void output(byte[] content) throws IOException, Exception {

        OutputStream out = null;
        try {

            out = getHttpServletResponse().getOutputStream();
            int lenContent = content.length;
            int offset = 0;
            int lengthPerWrite = 8192;

            if (lenContent > lengthPerWrite) {

                do {
                    if ((offset + lengthPerWrite) > lenContent) {
                        lengthPerWrite = lenContent - offset;
                    }
                    out.write(content, offset, lengthPerWrite);
                    offset = offset + lengthPerWrite;
                } while (offset < lenContent);

            } else {
                out.write(content);
            }

        } catch (IOException e) {
            log.error(e);
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw e;
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
            } catch (IOException e) {
                log.info(e);
            }

        }
    }

    /**
	 * 
	 */
    private static final long serialVersionUID = -2378927679657017510L;

}
