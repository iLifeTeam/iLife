package com.ilife.zhihu.service;

import com.ilife.zhihu.entity.*;

import java.util.List;


public interface ZhihuService {

    Question getQuestionById(String id);

    Article getArticleById(String id);

    Answer getAnswerById(String id);

    List<Activity> getUserActivity(String username);

    User getUserWithEmail(String email);

    void saveActivitiesFromJsonString(User user, String json);

    User saveUserFromJsonString(String email, String json);
}
