package com.bbkmobile.iqoo.explorer.logcache;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UpgradeQueryLogCache {

    private Log log = LogFactory.getLog(UpgradeQueryLogCache.class);

    public static UpgradeQueryLogCache getIns() {
        return ins.ins;
    }

    public void add(String log) {

        synchronized (lockForCache) {

            cache.add(log);

            if (cache.size() >= threshold()) {
                switchContainer();
            }

        }
    }

    /**
     * cache指向新的存储容器，并将cache中数据 存入LogWarehouse中。
     */
    private void switchContainer() {

        final List<String> tmpCache = cache;
        cache = newContainer();

        // new Thread(){
        // public void run(){
        // FactoryDeal.createDeal().Deal(tmpCache);
        // }
        // }.start();

        storeLogs(tmpCache);

    }

    /*
     * 自动处理log
     */
    protected void autoDealLogs() {
        synchronized (lockForCache) {
            if (cache.size() > 0) {
                switchContainer();
            }
        }
    }

    private void storeLogs(List<String> logs) {
        synchronized (queue) {
            queue.add(logs);
            queue.notifyAll();
        }
    }

    private int threshold() {
        return 10000;
    }

    private List<String> newContainer() {
        return new ArrayList<String>();
    }

    private static class ins {
        private static UpgradeQueryLogCache ins = new UpgradeQueryLogCache();
    }

    private void init() {

        cache = newContainer();
        LogProcessor processor = null;
        for (int i = 0; i < threadNum; i++) {
            processor = new LogProcessor();
            processor.start();
        }
        // 每10s中自动处理一次log
        new Timer("auto deal logs timer").schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    autoDealLogs();
                } catch (Exception e) {
                    log.error("自动处理log时出错", e);
                }

            }
        }, 10000, 10000);

    }

    private UpgradeQueryLogCache() {
        init();
    }

    private List<String> cache = null;
    private Object lockForCache = new Object();
    private int threadNum = 2;
    private List<List<String>> queue = new ArrayList<List<String>>();

    private class LogProcessor extends Thread {

        public void run() {
            List<String> logLs = null;

            while (true) {

                synchronized (queue) {
                    if (queue.size() <= 0) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            log.error(e);
                        }
                    }

                    log.info(this.getName() + ": " + queue.size());
                    if (queue.size() > 0) {
                        logLs = queue.remove(0);
                    } else {
                        continue;
                    }

                }

                try {
                    if (null == deal) {
                        deal = FactoryDeal.createDeal();
                    }

                    deal.Deal(logLs);
                } catch (Exception e) {
                    log.error("处理log时出错", e);
                }

            }
        }

        private Deal deal = null;

    }
}
