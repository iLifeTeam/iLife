package com.ilife.zhihu.dao.daoimpl;

import com.ilife.zhihu.dao.ActivityDao;
import com.ilife.zhihu.dao.AnswerDao;
import com.ilife.zhihu.entity.Answer;
import com.ilife.zhihu.repository.ActivityRepository;
import com.ilife.zhihu.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerDaoImpl implements AnswerDao {

    @Autowired
    private AnswerRepository answerRepository;


    @Override
    public Answer findAnswerById(Integer id) {
        return answerRepository.findById(id).orElse(null);
    }

    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public void deleteById(Integer id) {
        answerRepository.deleteById(id);
    }
}
