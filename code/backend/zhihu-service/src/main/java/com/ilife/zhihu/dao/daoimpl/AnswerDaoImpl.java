package com.ilife.zhihu.dao.daoimpl;

import com.ilife.zhihu.dao.ActivityDao;
import com.ilife.zhihu.dao.AnswerDao;
import com.ilife.zhihu.entity.Answer;
import com.ilife.zhihu.entity.Article;
import com.ilife.zhihu.repository.ActivityRepository;
import com.ilife.zhihu.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AnswerDaoImpl implements AnswerDao {

    @Autowired
    private AnswerRepository answerRepository;


    @Override
    public Answer findById(String id) {
        return answerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Answer> findByIds(List<String> ids) {
        List<Answer> answers = new ArrayList<>();
        answerRepository.findAllById(ids).forEach(answers::add);
        return answers;
    }

    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public void deleteById(String id) {
        answerRepository.deleteById(id);
    }
}
