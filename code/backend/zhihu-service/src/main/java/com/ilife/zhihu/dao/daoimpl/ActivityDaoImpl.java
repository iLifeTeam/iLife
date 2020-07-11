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
    public Activity findById(Integer id) {
        return activityRepository.findById(id).orElse(null);
    }

    @Override
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }


    @Override
    public void deleteById(Integer id) {
        activityRepository.deleteById(id);

    }

}
