package com.bbkmobile.iqoo.common.util;

/**  
 *PageJsoupUtil.java         2014年8月18日下午8:10:13
 *@Copyright:Copyright © 2014 VIVO Communication Technology Co., Ltd. All rights reserved.
 *@Company:Vivo----http://www.vivo.com.cn/
 * 
 */
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 返回某个页面的某个Class的某个属性，　一般用于取baidu的package
 * 
 * @Title:
 * @Description:
 * @Author:leez
 * @Since:2014年8月18日
 * @Modified By:
 * @Modified Date:
 * @Why & What is modified:
 * @Version:1.0
 */
public class PageJsoupUtil {

    /**
     * 取得页面URL的，　cssClass中属性为attr的值</br> 一般用来获取package或 docid
     * 
     * @Description:
     * @param url
     * @param cssClass
     * @param attr
     * @return
     * @Author:leez
     * @see:
     * @since: 1.0
     * @Create Date:2014年8月18日
     */
    public static String getValue(String url, String cssClass, String attr) {
        try {
            Document doc = Jsoup.parse(new URL(url), 30000);
            Elements ele = doc.getElementsByClass(cssClass);
            String value = ele.attr(attr);
            return value;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取百度的 docid
     * 
     * @Description:
     * @param url
     * @return
     * @Author:leez
     * @see:
     * @since: 1.0
     * @Create Date:2014年8月19日
     */
    public static String getBaiDuDocId(String url) {
        return getValue(url, "highspeed", "data-docid");
    }

    /**
     * 取得百度应用screen
     * 
     * @Description:
     * @param url
     * @param cssClass
     * @param attr
     *            图片的属性
     * @return
     * @Author:leez
     * @see:
     * @since: 1.0
     * @Create Date:2014年8月19日
     */
    public static List<String> getScreenshot(String url, String cssClass,
            String attr) {
        try {
            List<String> screenShotList = new ArrayList<String>();
            Document doc = Jsoup.parse(new URL(url), 30000);
            Elements elements = doc.getElementsByClass(cssClass);
            if (elements != null) {
                elements = elements.first().getElementsByTag("img");
                for (Element ele : elements) {
                    screenShotList.add(ele.attr(attr));
                }
                return screenShotList;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getBaiduScreenshotByDocid(String docId) {
        return PageJsoupUtil.getScreenshot(
                "http://shouji.baidu.com/game/item?docid=" + docId,
                "screenshot-container", "data-original");
    }

    public static void main(String args[]) {
        /*
         * List<String> screenShotList = PageJsoupUtil.getScreenshot(
         * "http://shouji.baidu.com/game/item?docid=6730357",
         * "screenshot-container", "data-original");
         * System.out.println(screenShotList);
         */
        String packageName = PageJsoupUtil.getValue(
                "http://shouji.baidu.com/game/item?docid=6730357", "highspeed",
                "data_package");
        System.out.println(packageName);
    }
}
