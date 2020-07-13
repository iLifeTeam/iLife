package com.ilife.zhihu.service;

import com.ilife.zhihu.entity.Activity;
import com.ilife.zhihu.entity.Question;
import com.ilife.zhihu.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ZhihuService {
    Question addQuestion(Question question);
    List<Activity> getUserActivity(String username);
    String getUserActivityJson(String username);
    User getUserWithEmail(String email);
    User getUserWithName(String name);
    void saveActivitiesFromJsonString(User user , String json);
    User saveUser(User user);
    User saveUserFromJsonString(String email, String json);
}
