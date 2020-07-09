package com.ilife.zhihu.dao;

import com.ilife.zhihu.entity.Question;

import javax.transaction.Transactional;
import java.util.List;

public interface QuestionDao {

    Question findQuestionById(Integer id);
    List<Question> findAllQuestionByIds(List<Integer> ids);
    Question save(Question question);
    void deleteById(Integer id);
}
