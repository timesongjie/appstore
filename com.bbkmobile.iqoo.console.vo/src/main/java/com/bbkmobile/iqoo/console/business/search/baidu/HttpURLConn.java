/**
 * 
 */
package com.bbkmobile.iqoo.console.business.search.baidu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import com.bbkmobile.iqoo.common.logging.Lg;
import com.bbkmobile.iqoo.console.constants.LgType;

import net.sf.json.JSONArray;

/**
 * @author wangbo
 * 
 */
public class HttpURLConn {

    public static String response(String http) {
        StringBuilder responseXml = new StringBuilder();
        HttpURLConnection con = null;
        BufferedReader is = null;
        try {
            URL url = new URL(http);

            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setReadTimeout(4000);
            con.setConnectTimeout(4000);
            // InputStream ins = con.getInputStream();
            // InputStreamReader insr = new InputStreamReader(ins);
            // is = new BufferedReader(insr);
            is = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while ((line = is.readLine()) != null) {
                responseXml.append(line);
            }
            is.close();
        } catch (Exception e) {
            // e.printStackTrace();
            Lg.error(LgType.STDOUT, "======HttpURLConn response，" + http
                    + ",error=" + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.disconnect();
                    con = null;
                }
                if (is != null) {
                    is.close();
                }

            } catch (IOException e) {
                Lg.error(LgType.STDOUT, "HttpURLConn response close，" + http
                        + ",error=" + e.getMessage());
            }
        }

        return responseXml.toString();
    }

    // update app
    public static String responseJson(String urlStr, String jsonStr) {
        StringBuilder responseXml = new StringBuilder();
        HttpURLConnection connection = null;
        DataOutputStream out = null;
        BufferedReader reader = null;
        try {
            // 创建连接
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setReadTimeout(30000);
            connection.setConnectTimeout(30000);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.connect();

            // POST请求
            if (null != jsonStr && !"".equals(jsonStr)) {
                out = new DataOutputStream(connection.getOutputStream());

                JSONArray jArrayFromStr = JSONArray.fromObject(jsonStr);

                out.writeBytes(jArrayFromStr.toString());
                out.flush();
                out.close();
            }

            // 读取响应
            reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;

            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                responseXml.append(lines);
            }

            // System.out.println(responseXml); //update app log

            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            Lg.error(LgType.APPUPDATE, "app百度更新responseJson，urlStr=" + urlStr
                    + ",error:" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            Lg.error(LgType.APPUPDATE, "app百度更新responseJson，urlStr=" + urlStr
                    + ",error:" + e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            Lg.error(LgType.APPUPDATE, "app百度更新responseJson，urlStr=" + urlStr
                    + ",error:" + e.getMessage());
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
                if (connection != null) {
                    connection.disconnect();
                    connection = null;
                }
                if (reader != null) {
                    reader.close();
                }

            } catch (IOException e) {
                Lg.error(LgType.APPUPDATE, "app百度更新responseJson，urlStr="
                        + urlStr + ",error:" + e.getMessage());
            }
        }
        return responseXml.toString();

    }

}
