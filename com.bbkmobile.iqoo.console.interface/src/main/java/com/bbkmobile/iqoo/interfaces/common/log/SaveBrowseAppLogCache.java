package com.bbkmobile.iqoo.interfaces.common.log;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class SaveBrowseAppLogCache extends AbstractLogCache {

    @Resource(name = "saveBrowseAppLogCacheDeal")
    private Deal deal;

    @Override
    public Deal createDeal() {
        return deal;
    }

}
