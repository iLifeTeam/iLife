package com.ilife.zhihu.dao;

import com.ilife.zhihu.entity.Answer;

import java.util.List;

public interface AnswerDao {

    Answer findById(String id);

    List<Answer> findByIds(List<String> ids);

    Answer save(Answer answer);

    void deleteById(String id);
}
