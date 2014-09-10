package com.bbkmobile.iqoo.interfaces.common.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bbkmobile.iqoo.common.util.cfgfile.PropertiesFileManager;

public abstract class AbstractLogCache {

    private Log log = LogFactory.getLog(AbstractLogCache.class);

    public void add(String... param) {

        synchronized (lockForCache) {
            cache.add(param);
            if (cache.size() >= getHold()) {
                switchContainer();
            }

        }
    }

    /**
     * cache指向新的存储容器，并将cache中数据 存入LogWarehouse中。
     */
    private void switchContainer() {

        final List<String[]> tmpCache = cache;
        cache = newContainer();
        storeLogs(tmpCache);

    }

    private void storeLogs(List<String[]> logs) {
        synchronized (queue) {
            queue.add(logs);
            queue.notifyAll();

            long current = System.currentTimeMillis();
            if (queue.size() > alarmLimit && current - alarmTime > alarmGrap) {
                alarmTime = current;
                incrProcessor(alarmInrSize);
            }
            ;

        }
    }

    /*
     * 自动处理log
     */
    private void autoDealLogs() {
        synchronized (lockForCache) {
            if (cache.size() > 0) {
                switchContainer();
            }
        }
    }

    private List<String[]> newContainer() {
        return new ArrayList<String[]>();
    }

    private void init() {
        cache = newContainer();
        for (int i = 0; i < threadNum; i++) {
            cachedThreadPool.execute(new LogProcessor());
        }
        // 每15s中自动处理一次log
        timer = new Timer("auto deal logs timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    autoDealLogs();
                } catch (Exception e) {
                    log.error("自动处理log时出错", e);
                }
            }
        }, getGrap(), getGrap());
    }

    protected AbstractLogCache() {
        init();
    }

    private List<String[]> cache = null;
    private Object lockForCache = new Object();
    private int hold = 10000;
    private int grap = 15000;
    private List<List<String[]>> queue = new ArrayList<List<String[]>>();
    private int alarmLimit = 5;// 警告值
    private int alarmGrap = 60000;// 警告处理间隔时间点（ms）
    private long alarmTime = 0;// 警告处理时间点(ms)
    private int alarmInrSize = 2;
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    private Timer timer = null;
    private int threadNum = 4;
    private int maxThread = 8;

    private class LogProcessor extends Thread {
        private boolean closed = false;

        public void run() {
            List<String[]> logLs = null;
            while (!closed) {
                synchronized (queue) {
                    if (queue.size() <= 0) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            log.error(e);
                        }
                    }
                    if (queue.size() > 0) {
                        logLs = queue.remove(0);
                    } else {
                        continue;
                    }

                }
                try {
                    if (null == deal) {
                        deal = createDeal();
                    }
                    deal.deal(logLs);
                } catch (Exception e) {
                    log.error("处理log时出错", e);
                }
            }
        }

        private Deal deal = null;
    }

    public int getSize() {
        return queue.size();
    }

    public abstract Deal createDeal();

    public int getCacheSize() {
        if (cache != null) {
            return cache.size();
        }
        return -1;
    }

    public int getHold() {
        String log_hold = PropertiesFileManager.getInstance()
                .getValueFromPropFile("log_hold");
        if (log_hold != null && log_hold.trim().length() > 0
                && StringUtils.isNumeric(log_hold)) {
            hold = Integer.parseInt(log_hold);
        }
        return hold;
    }

    public void setHold(int hold) {
        this.hold = hold;
    }

    public int getGrap() {
        String log_gap = PropertiesFileManager.getInstance()
                .getValueFromPropFile("log_gap");
        if (log_gap != null && log_gap.trim().length() > 0
                && StringUtils.isNumeric(log_gap)) {
            grap = Integer.parseInt(log_gap);
        }
        return grap;
    }

    private void incrProcessor(int incr) {
        if (threadNum < maxThread) {
            for (int i = 0; i < incr; i++) {
                cachedThreadPool.execute(new LogProcessor());
            }
            threadNum += incr;
        }
    }
}
