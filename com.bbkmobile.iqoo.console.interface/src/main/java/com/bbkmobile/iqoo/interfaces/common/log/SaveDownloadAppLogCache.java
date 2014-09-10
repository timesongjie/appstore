package com.bbkmobile.iqoo.interfaces.common.log;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class SaveDownloadAppLogCache extends AbstractLogCache {

    @Resource(name = "saveDownloadAppLogCacheDeal")
    private Deal deal;

    @Override
    public Deal createDeal() {
        return deal;
    }

}
