package com.ilife.zhihu.service;

import com.ilife.zhihu.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ZhihuService {
    Question addQuestion(Question question);

    Question getQuestionById(Integer id);
    Article getArticleById(Integer id);
    Answer getAnswerById(Integer id);
    String getQuestionJsonById(Integer id);
    String getArticleJsonById(Integer id);
    String getAnswerJsonById(Integer id);

    List<Activity> getUserActivity(String username);
    String getUserActivityJson(String username);
    User getUserWithEmail(String email);
    User getUserWithName(String name);
    void saveActivitiesFromJsonString(User user , String json);
    User saveUser(User user);
    User saveUserFromJsonString(String email, String json);
}
