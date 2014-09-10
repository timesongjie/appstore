package com.bbkmobile.iqoo.common.util;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainTest {
    public static void cleanSvn(String path, String fileName) {
        if (fileName == null || "".equals(fileName.trim())) {
            return;
        }
        File file = new File(path);
        if (file.isDirectory()) {
            if (fileName.equals(file.getName())) {
                deleteDirectory(file.getAbsolutePath());
            } else {
                File[] files = file.listFiles();
                for (File f : files) {
                    cleanSvn(f.getAbsolutePath(), fileName);
                }
            }
        } else {
            if (fileName.equals(file.getName())) {
                file.delete();
            }
        }
    }

    public static void deleteDirectory(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isFile()) {
                    boolean b = f.delete();
                    System.out.println("delete!" + b);
                } else {
                    deleteDirectory(f.getAbsolutePath());
                }
            }
            file.delete();
        }
    }

    /** 获取List集合中某特定属性和属性值的对象 **/
    public static <T> T getPointObj(List<T> objs, String prop, Object value) {
        if (objs != null && objs.size() > 0) {
            for (T obj : objs) {
                if (value == (getPropValue(obj.getClass(), prop, obj))) {
                    return obj;
                }
                ;
            }
            return null;
        }
        return null;
    }

    public static Object getPropValue(Class clazz, String fieldName, Object obj) {

        if (!clazz.isAssignableFrom(obj.getClass())) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("get");
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        try {
            Method get = clazz.getMethod(sb.toString());
            return get.invoke(obj);
        } catch (Exception e) {
        }
        return null;
    }

    public static void main(String[] args) {
        cleanSvn("/home/time/workspace/clearly/console", ".svn");

        // long time = (new Date().getTime()) - Long.valueOf("1366515802390");
        // Date d = new Date();
        // d.setTime(1366515802390l);
        // System.out.println(d);
        // System.out.println();
        // if(time > 24*60*1000){//BUG <改成>
        // System.out.println("超时");
        // }else{
        // System.out.println("未超时");
        // }
        // Man m = new Man();
        // m.setName("time");
        //
        // Man m2 = new Man();
        // m2.setName("time2");
        //
        // Man m3 = new Man();
        // m3.setName("time33");
        //
        // List<Man> ps = new ArrayList<Man>(2);
        // ps.add(m2);
        // ps.add(m);
        //
        // MainTest test = new MainTest();
        // String name = (String) getPropValue(Person.class, "name", m);
        // System.out.println(name);
        // System.out.println(getPointObj(ps,"name","time").getSex());
    }

}
