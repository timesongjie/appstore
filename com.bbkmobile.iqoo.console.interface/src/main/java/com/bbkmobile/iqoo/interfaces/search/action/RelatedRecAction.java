package com.bbkmobile.iqoo.interfaces.search.action;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bbkmobile.iqoo.common.json.NullResultObject;
import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.console.constants.Constants;
import com.bbkmobile.iqoo.console.constants.LgType;
import com.bbkmobile.iqoo.interfaces.search.business.RelatedRecService;
import com.bbkmobile.iqoo.platform.base.BaseStreamAction;

@Component("iRelatedRecAction")
@Scope("prototype")
public class RelatedRecAction extends BaseStreamAction {

    /**
     * 
     */
    private static final long serialVersionUID = -5386298835024448718L;

    @Resource(name = "iRelatedRecService")
    private RelatedRecService relatedRecService;

    private String model;
    private String package_name;
    private String app_version;

    public String relatedRecApps() throws Exception {

        try {
            String data = relatedRecService.getRelatedRecApps(package_name,
                    model, app_version);
            if (StringUtils.isNotBlank(app_version)
                    && StringUtils.isNumeric(app_version)
                    && Float.valueOf(app_version) >= Constants.APPVERSION530) {
                write(data, "text/plain;charset=utf8");
            } else {
                write(data, "text/xml;charset=utf8");
            }
        } catch (Exception e) {
            outwrite(NullResultObject.print(), "text/plain;charset=utf-8");
            Lg.error(LgType.STDOUT, "获取app相关推荐时出错: error=" + e);
        }
        return null;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPackage_name() {
        return this.package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getApp_version() {
        return this.app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }
}
