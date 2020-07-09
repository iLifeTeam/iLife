package com.ilife.zhihu.dao;

import com.ilife.zhihu.entity.Question;

import javax.transaction.Transactional;

public interface QuestionDao {

    Question findQuestionById(Integer id);

    Question save(Question question);
    void deleteById(Integer id);
}
