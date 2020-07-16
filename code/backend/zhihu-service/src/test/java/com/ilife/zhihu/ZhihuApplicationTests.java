package com.ilife.zhihu;

import com.alibaba.fastjson.JSON;
import com.ilife.zhihu.dao.AnswerDao;
import com.ilife.zhihu.dao.QuestionDao;
import com.ilife.zhihu.entity.Answer;
import com.ilife.zhihu.entity.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
class ZhihuApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    QuestionDao questionDao;
    @Autowired
    AnswerDao answerDao;

    @Test
    void AnswerDaoWriteTest() {
        Question question = new Question();
        question.setContent("second question");
        question.setTitle("question2_title");
        question.setId("question1");
        Question saved = questionDao.save(question);
        Answer answer = new Answer();
        answer.setId("answer0");
        answer.setAuthor("adsgasdf");
        answer.setQuestion(saved);
        answerDao.save(answer);
        Answer answer1 = new Answer();
        answer.setId("answer1");
        answer1.setAuthor("akdsfadsf");
        answer1.setQuestion(saved);
        answerDao.save(answer1);

        answerDao.deleteById(answer.getId());
        answerDao.deleteById(answer1.getId());
        questionDao.deleteById(question.getId());
    }

    @Test
    @Transactional
    void AnswerDaoReadTest(){
        Question question = questionDao.findById("123");
        if(question == null){
            return;
        }
        for (Answer answer : question.getAnswerList()){
            System.out.println(answer.getExcerpt());
        }
    }


}
