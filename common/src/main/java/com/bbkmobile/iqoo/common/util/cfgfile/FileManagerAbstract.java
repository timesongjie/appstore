/**
 * 
 */
package com.bbkmobile.iqoo.common.util.cfgfile;

/**
 * @author wangbo
 */
public abstract class FileManagerAbstract {

    private CfgFileInterface cfgFile;

    /**
     * 构造器
     */
    protected FileManagerAbstract() {
        init();
    }

    /**
     * 初始化方法
     */
    private void init() {
        cfgFile = createCfgFile();
    }

    /**
     * 
     * @return
     */
    protected abstract CfgFileInterface createCfgFile();

    /**
     * 
     * @param key
     * @return value
     */
    public String getProperty(String key) {

        return cfgFile.getValue(key);
    }

    public String getValueFromPropFile(String key) {
        return cfgFile.getValueFromPropFile(key);
    };

    public String[] getArrayFromPropFile(String key) {
        return cfgFile.getArrayFromPropFile(key);
    };
}
