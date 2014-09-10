package com.bbkmobile.iqoo.console.dao.recommendation;

import java.util.List;

public class ResponseDateWithCprd {
    public String generateXml(List<CellphoneRecommendApp> cellphoneRecommendApps, int apps_per_page, int page_index ,int total_count) throws Exception {
        
        StringBuilder sb = new StringBuilder();
        
        int maxpage = (int)Math.ceil((float)total_count/apps_per_page);

        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?><PackageList maxpage=\""); 
        sb.append(maxpage);
        sb.append("\" PageNo=\"");
        sb.append(page_index);
        sb.append("\" pageSize=\"");
        sb.append(apps_per_page);
        sb.append("\" TotalCount=\"");
        sb.append(total_count);
        sb.append("\">");
        
          
        if(null != cellphoneRecommendApps && cellphoneRecommendApps.size()>0){
           
            for(CellphoneRecommendApp cellphoneRecommendApp : cellphoneRecommendApps){
                sb.append("<Package><id><![CDATA[");
                sb.append(cellphoneRecommendApp.getAppInfo().getId());
                sb.append("]]></id><package_name><![CDATA[");
                sb.append(cellphoneRecommendApp.getAppInfo().getAppPackage());
                sb.append("]]></package_name><parent_id><![CDATA[");
                sb.append(cellphoneRecommendApp.getAppInfo().getAppType().getParentId());
                sb.append("]]></parent_id><title_zh><![CDATA[");
                sb.append(cellphoneRecommendApp.getAppInfo().getAppCnName());
                sb.append("]]></title_zh><title_en><![CDATA[");
                sb.append(cellphoneRecommendApp.getAppInfo().getAppEnName());
                sb.append("]]></title_en><icon_url><![CDATA[");
                sb.append(cellphoneRecommendApp.getAppInfo().getAppIcon());
                sb.append("]]></icon_url><developer><![CDATA[");
                sb.append(cellphoneRecommendApp.getAppInfo().getAppAuthor());
                sb.append("]]></developer><score><![CDATA[");
                sb.append(cellphoneRecommendApp.getAppInfo().getAvgComment());
                sb.append("]]></score><raters_count><![CDATA[");
                sb.append(cellphoneRecommendApp.getAppInfo().getCommentCount());
                sb.append("]]></raters_count><version_name><![CDATA[");
                sb.append(cellphoneRecommendApp.getAppInfo().getAppVersion());
                sb.append("]]></version_name><version_code><![CDATA[");
                sb.append(cellphoneRecommendApp.getAppInfo().getAppVersionCode());
                sb.append("]]></version_code></Package>");      
            }
        }
       
        sb.append("</PackageList>");
        
        return sb.toString();
    }

}
