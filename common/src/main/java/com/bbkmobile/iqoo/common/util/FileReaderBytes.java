/**
 * 
 */
package com.bbkmobile.iqoo.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author wangbo
 * @version 1.0.0.0/2011-8-10
 */
public class FileReaderBytes {

    public static byte[] readFileByByte(File file) {
        byte[] fileContent = null;
        try {
            fileContent = new byte[Long.valueOf(file.length()).intValue()];
            FileInputStream fis = new FileInputStream(file);
            byte[] fileBuff = new byte[1024];
            int offset = 0;
            int len = 0;

            while (-1 != (len = fis.read(fileBuff))) {
                System.arraycopy(fileBuff, 0, fileContent, offset, len);
                offset = offset + len;
            }

        } catch (IOException e) {
            Log log = LogFactory.getLog(FileReaderBytes.class);
            log.error("读取文件(" + file.getName() + ")失败：" + e);
        }

        return fileContent;
    }
}
