/**
 * 
 */
package com.bbkmobile.iqoo.platform.auth;

import com.bbkmobile.iqoo.platform.ReturnVO;

/**
 * @author wangbo
 * @version 1.0.0.0/2011-12-21
 */
public interface Auth {

    /**
     * 验证用户是否存在
     * 
     * @param user
     * @return ReturnVO<Boolean> 用户存在ReturnVO.getRet == true
     */
    ReturnVO<Boolean> userExsits(User user);

    /**
     * 用户是否有该资源的操作权限
     * 
     * @param user
     *            用户
     * @param resource
     *            用户需要操作的资源对象
     * @param operateType
     *            操作类型：增、删、改、查等
     * @return ReturnVO<Boolean> 有操作权限ReturnVO.getRet == true
     */
    ReturnVO<Boolean> hasPrivilege(User user, Resource resource,
            String operateType);
}
