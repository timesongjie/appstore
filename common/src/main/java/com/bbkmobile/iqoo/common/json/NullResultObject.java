package com.bbkmobile.iqoo.common.json;

/**
 * 服务器异常返回
 * 
 * @author time
 * 
 */
public class NullResultObject extends ResultObject<Object> {
    private static NullResultObject nullResult = new NullResultObject();

    private NullResultObject() {
        setResult(false);
    }

    public static NullResultObject getInstance() {
        return nullResult;
    }

    public static String print() {
        return JackSonParser.bean2Json(nullResult);
    }
}
