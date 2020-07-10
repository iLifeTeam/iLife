package com.ilife.zhihu.service;

import com.ilife.zhihu.entity.Activity;
import com.ilife.zhihu.entity.Question;
import org.springframework.stereotype.Service;


public interface ZhihuService {
    Question addQuestion(Question question);
    Activity getUserActivity(String zhihuId);
    Integer saveActivitiesFromJsonString(String username ,String json);
}
