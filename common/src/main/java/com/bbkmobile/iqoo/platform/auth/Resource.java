/**
 * 
 */
package com.bbkmobile.iqoo.platform.auth;

import java.io.Serializable;

/**
 * 资源类
 * 
 * @author wangbo
 * @version 1.0.0.0/2011-12-21
 */
public class Resource implements Serializable {

    private static final long serialVersionUID = -4849258206924618033L;

    // 资源ID
    private Integer resourceID;
    // 资源
    private String resource;

    /**
     * 资源ID
     * 
     * @return
     */
    public Integer getResourceID() {

        return resourceID;
    }

    /**
     * 资源字符串
     * 
     * @return
     */
    public String getResource() {

        return resource;
    }

    public void setResourceID(Integer resourceID) {
        this.resourceID = resourceID;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

}
