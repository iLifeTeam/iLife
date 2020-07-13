package com.ilife.zhihu.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.ilife.zhihu.dao.QuestionDao;
import com.ilife.zhihu.entity.Question;
import com.ilife.zhihu.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionDao questionDao;

    @Override
    public Question saveQuestionFromJsonObject(JSONObject questionObject) {
        return null;
    }

    @Override
    public Question getQuestionById(Integer id) {
        return questionDao.findQuestionById(id);
    }
}
