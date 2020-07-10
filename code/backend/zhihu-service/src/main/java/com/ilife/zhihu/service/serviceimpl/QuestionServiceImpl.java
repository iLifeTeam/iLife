package com.ilife.zhihu.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.ilife.zhihu.entity.Question;
import com.ilife.zhihu.service.QuestionService;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Override
    public Question saveQuestionFromJsonObject(JSONObject questionObject) {
        return null;
    }
}
