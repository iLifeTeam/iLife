package com.ilife.zhihu.service;

import com.ilife.zhihu.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ZhihuService {

    Question getQuestionById(String id);
    Article getArticleById(String id);
    Answer getAnswerById(String id);
    List<Activity> getUserActivity(String username);
    User getUserWithEmail(String email);
    void saveActivitiesFromJsonString(User user , String json);
    User saveUser(User user);
    User saveUserFromJsonString(String email, String json);
}
