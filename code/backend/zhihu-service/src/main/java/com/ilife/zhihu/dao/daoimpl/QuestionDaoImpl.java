package com.ilife.zhihu.dao.daoimpl;

import com.ilife.zhihu.dao.ArticleDao;
import com.ilife.zhihu.dao.QuestionDao;
import com.ilife.zhihu.entity.Article;
import com.ilife.zhihu.entity.Question;
import com.ilife.zhihu.repository.ArticleRepository;
import com.ilife.zhihu.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question findQuestionById(Integer id) {
        return questionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Question> findAllQuestionByIds(List<Integer> ids) {
        List<Question> questions = new ArrayList<>();
        questionRepository.findAllById(ids).forEach(questions::add);
        return questions;
    }

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void deleteById(Integer id) {
        questionRepository.deleteById(id);
    }
}
