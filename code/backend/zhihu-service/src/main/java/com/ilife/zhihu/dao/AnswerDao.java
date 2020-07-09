package com.ilife.zhihu.dao;

import com.ilife.zhihu.entity.Answer;

import java.util.List;

public interface AnswerDao {

    Answer findAnswerById(Integer id);
    List<Answer> findAnswerByIds(List<Integer> ids);
    Answer save(Answer answer);
    void deleteById(Integer id);
}
