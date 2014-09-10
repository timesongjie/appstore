package com.bbkmobile.iqoo.interfaces.activity.dao;

import java.util.List;

import com.bbkmobile.iqoo.console.activity.dao.ActivityInfo;
import com.bbkmobile.iqoo.console.activity.dao.ModelActivity;

public interface ActivityDAO {
    List<ModelActivity> getModelActivities() throws Exception;

    public ActivityInfo getModelActivityById(Integer id) throws Exception;

    public List getModelActivityIdByAppId(Long app_id) throws Exception;

}
