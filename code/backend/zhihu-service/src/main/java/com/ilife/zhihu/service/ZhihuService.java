package com.ilife.zhihu.service;

import com.ilife.zhihu.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ZhihuService {

    Question getQuestionById(Integer id);
    Article getArticleById(Integer id);
    Answer getAnswerById(Integer id);
    List<Activity> getUserActivity(String username);
    User getUserWithEmail(String email);
    void saveActivitiesFromJsonString(User user , String json);
    User saveUser(User user);
    User saveUserFromJsonString(String email, String json);
}
