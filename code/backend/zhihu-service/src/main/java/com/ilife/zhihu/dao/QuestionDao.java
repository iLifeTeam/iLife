package com.ilife.zhihu.dao;

import com.ilife.zhihu.entity.Question;

import javax.transaction.Transactional;
import java.util.List;

public interface QuestionDao {

    Question findById(String id);
    List<Question> findByIds(List<String> ids);
    Question save(Question question);
    void deleteById(String id);
}
