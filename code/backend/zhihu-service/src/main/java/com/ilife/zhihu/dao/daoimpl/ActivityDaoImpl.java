package com.ilife.zhihu.dao.daoimpl;

import com.ilife.zhihu.dao.ActivityDao;
import com.ilife.zhihu.entity.Activity;
import com.ilife.zhihu.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActivityDaoImpl implements ActivityDao {

    @Autowired
    private ActivityRepository activityRepository;


    @Override
    public List<Activity> findAllActivityByZhihuId(String zhihuId) {
        return activityRepository.findAllByZhihuId(zhihuId);
    }
    @Override
    public List<Activity> findAllActivityByZhihuIdAndType(String zhihuId, String type) {
        return activityRepository.findAllByZhihuIdAndType(zhihuId,type);
    }

    @Override
    public Activity findById(Integer id) {
        return activityRepository.findById(id).orElse(null);
    }

    @Override
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public void deleteByZhihuId(String zhihuId) {
        activityRepository.deleteByZhihuId(zhihuId);
    }

    @Override
    public void deleteById(Integer id) {
        activityRepository.deleteById(id);

    }

}
