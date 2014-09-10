package com.bbkmobile.iqoo.interfaces.push.business;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bbkmobile.iqoo.console.dao.appinfo.RequestParameter;
import com.bbkmobile.iqoo.interfaces.push.dao.IPushDao;
import com.bbkmobile.iqoo.interfaces.push.vo.PushActivity;

@Service
public class IPushServiceImpl implements IPushService {

    @Resource
    private IPushDao iPushDaoImpl;
    private Random random = new Random(1);

    @Override
    public PushActivity getPushActivity(RequestParameter requestParameter,
            PushActivity push) throws Exception {
        // version 1 根据pid 随机返回子活动信息
        List<PushActivity> list = iPushDaoImpl.getPushActivites();
        if (list != null && list.size() > 0) {
            int seed = list.size();
            int index = rand(seed);
            PushActivity temp = list.get(index);
            if (temp != null && temp.getRelationType() != null
                    && '2' != temp.getRelationType()) {
                Character relationType = temp.getRelationType();
                if ('0' == (relationType)) {// 应用
                    List result = iPushDaoImpl
                            .getAppInfoRelations(temp.getId());
                    if (result != null && result.size() > 0) {
                        Object[] obj = (Object[]) result.get(0);
                        temp.setPackageName(String.valueOf(obj[1]));
                        temp.setVersionCode(String.valueOf(obj[0]));
                    }
                } else if ('1' == (relationType)) {// 活动
                    List result = iPushDaoImpl.getActAppInfoRelations(temp
                            .getId());
                    if (result != null && result.size() > 0) {
                        Object[] obj = (Object[]) result.get(0);
                        temp.setPackageName(String.valueOf(obj[1]));
                        temp.setVersionCode(String.valueOf(obj[0]));
                    }
                }
            }
            return temp;
        }
        return null;
    }

    @Override
    public String getNewestPid() throws Exception {
        return iPushDaoImpl.getPid();
    }

    public int rand(int rang) {
        return random.nextInt(rang);
    }

    @Override
    public void savePushLogTable(String appVersion, String model, String pid,
            String pushid, long elapsedtime, String cs, String imei, String ip)
            throws Exception {
        iPushDaoImpl.savePushLogTable(appVersion, model, pid, pushid,
                elapsedtime, cs, imei, ip);

    }
}
