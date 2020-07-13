package com.ilife.zhihu.service;


import com.alibaba.fastjson.JSONObject;
import com.ilife.zhihu.entity.Question;

public interface QuestionService {
    Question saveQuestionFromJsonObject(JSONObject questionObject);
    Question getQuestionById(Integer id);
}
