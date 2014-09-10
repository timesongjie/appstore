package com.bbkmobile.iqoo.interfaces.common.log;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class SaveBrowseLogCache extends AbstractLogCache {

    @Resource(name = "saveBrowseLogCacheDeal")
    private Deal deal;

    @Override
    public Deal createDeal() {
        return deal;
    }
}
