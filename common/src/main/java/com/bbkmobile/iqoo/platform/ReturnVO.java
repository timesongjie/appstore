/**
 * 
 */
package com.bbkmobile.iqoo.platform;

import java.io.Serializable;

/**
 * 返回VO类
 * 
 * ret 存放真实的返回对象 retCode 返回码 keyForCode
 * 与返回码对应的key值，该值对应资源文件viewResources.properties中的提示语
 * 
 * @author wangbo
 * @version 1.0.0.0/2011-12-21
 */
public class ReturnVO<T> implements Serializable {

    // 返回码
    // default 0：错误
    private int retCode = 0;
    // 对应返回码的key值
    private String keyForCode;
    // 返回对象
    private T ret;
    // 返回消息
    private String retMsg;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        keyForCode = "message.code." + retCode;
        this.retCode = retCode;
    }

    public String getKeyForCode() {
        return keyForCode;
    }

    public T getRet() {
        return ret;
    }

    public void setRet(T ret) {
        this.ret = ret;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    /**
	 * 
	 */
    private static final long serialVersionUID = 1169748928122333172L;
}
