package com.bbkmobile.iqoo.interfaces.common.log;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component("saveSeachWordLogCache")
public class SaveSeachWordLogCache extends AbstractLogCache {

    @Resource(name = "saveSeachWordLogCacheDeal")
    private Deal deal;

    @Override
    public Deal createDeal() {
        return deal;
    }
}
