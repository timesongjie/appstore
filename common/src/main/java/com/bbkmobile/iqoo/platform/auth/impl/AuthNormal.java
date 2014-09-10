/**
 * 
 */
package com.bbkmobile.iqoo.platform.auth.impl;

import com.bbkmobile.iqoo.platform.ReturnVO;
import com.bbkmobile.iqoo.platform.auth.Resource;
import com.bbkmobile.iqoo.platform.auth.User;

/**
 * 该类待完善
 * 
 * @author wangbo
 * @version 1.0.0.0/2011-12-21
 */
public class AuthNormal implements com.bbkmobile.iqoo.platform.auth.Auth {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bbkmobile.iqoo.platform.auth.impl.Auth#hasPrivilege(com.bbkmobile
     * .iqoo.platform.auth.impl.User,
     * com.bbkmobile.iqoo.platform.auth.impl.Resource)
     */
    public ReturnVO<Boolean> hasPrivilege(User user, Resource resource,
            String operateType) {
        // TODO Auto-generated method stub
        ReturnVO<Boolean> returnVO = new ReturnVO<Boolean>();

        returnVO.setRet(Boolean.TRUE);
        returnVO.setRetCode(1);

        return returnVO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bbkmobile.iqoo.platform.auth.impl.Auth#userExsits(com.bbkmobile.iqoo
     * .platform.auth.impl.User)
     */
    public ReturnVO<Boolean> userExsits(User user) {
        // TODO Auto-generated method stub
        ReturnVO<Boolean> returnVO = new ReturnVO<Boolean>();

        returnVO.setRet(Boolean.TRUE);
        returnVO.setRetCode(1);

        return returnVO;
    }

}
