package com.ilife.zhihu.service;

import com.ilife.zhihu.entity.Activity;
import com.ilife.zhihu.entity.Question;
import com.ilife.zhihu.entity.User;
import org.springframework.stereotype.Service;


public interface ZhihuService {
    Question addQuestion(Question question);
    Activity getUserActivity(String zhihuId);
    User getUserWithEmail(String email);
    User getUserWithName(String name);
    void saveActivitiesFromJsonString(User user , String json);
    User saveUser(User user);
    User saveUserFromJsonString(String json);
}
