package com.bbkmobile.iqoo.interfaces.common.log;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class SavePackagesUpdateLogCache extends AbstractLogCache {

    @Resource(name = "savePackagesUpdateLogCacheDeal")
    private Deal deal;

    @Override
    public Deal createDeal() {
        return deal;
    }

}
