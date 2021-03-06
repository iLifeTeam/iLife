package com.ilife.zhihu.service.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ilife.zhihu.dao.*;
import com.ilife.zhihu.entity.*;
import com.ilife.zhihu.service.ZhihuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
public class ZhihuServiceImpl implements ZhihuService {

    @Autowired
    QuestionDao questionDao;
    @Autowired
    ActivityDao activityDao;
    @Autowired
    AnswerDao answerDao;
    @Autowired
    ArticleDao articleDao;
    @Autowired
    UserDao userDao;

    @Override
    public Question getQuestionById(String id) {
        return questionDao.findById(id);
    }

    @Override
    public Article getArticleById(String id) {
        return articleDao.findById(id);
    }

    @Override
    public Answer getAnswerById(String id) {
        return answerDao.findById(id);
    }

    @Override
    @Transactional
    public List<Activity> getUserActivity(String email) {
        User user = userDao.findByEmail(email);
        return user.getActivities();
    }

    private Timestamp convertEpochToTimestamp(Long epoch) {
        return new Timestamp(epoch * 1000);
    }

    private Activity makeActivityFromJsonObject(JSONObject activityObject) {
        Activity activity = new Activity();
        activity.setId(activityObject.getString("id"));
        activity.setAction_text(activityObject.getString("action_text"));
        activity.setCreated_time(
                convertEpochToTimestamp(activityObject.getLong("create_time")));
        activity.setType(activityObject.getString("type"));
        return activity;
    }

    private Question makeQuestionFromJsonObject(JSONObject questionObject) {
        Question question = new Question();
        question.setId(questionObject.getString("id"));
        question.setTitle(questionObject.getString("title"));
        question.setContent(questionObject.getString("content"));
        question.setExcerpt(questionObject.getString("excerpt"));
        question.setAnswer_count(questionObject.getInteger("answer_count"));
        question.setCreated_time(
                convertEpochToTimestamp(questionObject.getLong("create_time")));
        question.setUpdate_time(
                convertEpochToTimestamp(questionObject.getLong("update_time")));
        return question;
    }

    private Answer makeAnswerFromJsonObject(JSONObject answerObject) {
        Answer answer = new Answer();
        answer.setId(answerObject.getString("id"));
        answer.setAuthor(answerObject.getString("author"));
        answer.setComment_count(answerObject.getInteger("comment_count"));
        answer.setContent(answerObject.getString("content"));
        answer.setExcerpt(answerObject.getString("excerpt"));
        answer.setCreated_time(convertEpochToTimestamp(answerObject.getLong("create_time")));
        answer.setUpdate_time(convertEpochToTimestamp(answerObject.getLong("update_time")));
        answer.setVoteup_count(answerObject.getInteger("voteup_count"));
        return answer;
    }

    private Article makeArticleFromJsonObject(JSONObject articleObject) {
        Article article = new Article();
        article.setId(articleObject.getString("id"));
        article.setAuthor(articleObject.getString("author"));
        article.setContent(articleObject.getString("content"));
        article.setExcerpt(articleObject.getString("excerpt"));
        article.setColumn_name(articleObject.getString("column_name"));
        article.setImage_url(articleObject.getString("image_url"));
        article.setTitle(articleObject.getString("title"));
        article.setUpdate_time(convertEpochToTimestamp(articleObject.getLong("update_time")));
        return article;
    }

    @Override
    @Transactional
    public void saveActivitiesFromJsonString(User user, String json) {
        JSONArray jsonArray = JSON.parseArray(json);
        for (Object object : jsonArray) {
            JSONObject activityObject = (JSONObject) object;
//            System.out.println(activityObject.toJSONString());
            Activity activity = makeActivityFromJsonObject(activityObject);
            System.out.println(activity.getCreated_time().toString() + activity.getAction_text());
            activity.setUser(user);
            switch (activity.getType()) {
                case "CREATE_QUESTION":
                case "FOLLOW_QUESTION": {
                    JSONObject questionObject = activityObject.getJSONObject("question");
//                    System.out.println(questionDao);
//                    System.out.println(questionObject.toJSONString());
                    Question question = questionDao.save(
                            makeQuestionFromJsonObject(questionObject));
                    JSONArray answerArray = questionObject.getJSONArray("answers");
                    for (Object answerObject : answerArray) {
                        Answer answer = makeAnswerFromJsonObject((JSONObject) answerObject);
                        answer.setQuestion(question);
                        answerDao.save(answer);
                    }
                    activity.setTarget_id(question.getId());
                    break;
                }
                case "CREATE_ANSWER":
                case "VOTEUP_ANSWER": {
                    JSONObject answerObject = (JSONObject) activityObject.get("answer");
                    Answer answer = makeAnswerFromJsonObject(answerObject);
                    Question question = questionDao.save(
                            makeQuestionFromJsonObject(answerObject.getJSONObject("question")));
                    answer.setQuestion(question);
                    answerDao.save(answer);
                    activity.setTarget_id(answer.getId());
                    break;
                }
                case "CREATE_ARTICLE":
                case "VOTEUP_ARTICLE": {
                    JSONObject articleObject = (JSONObject) activityObject.get("article");
                    Article article = makeArticleFromJsonObject(articleObject);
                    articleDao.save(article);
                    activity.setTarget_id(article.getId());
                    break;
                }
            }
            activityDao.save(activity);
        }
    }

    @Override
    public User saveUserFromJsonString(String email, String json) {
//        JSONObject userObject = JSON.parseObject(json);
//        User user = makeUserFromJsonObject(userObject);
        User user = JSON.parseObject(json, User.class);
        user.setEmail(email);
        System.out.println(JSON.toJSONString(user));
        return userDao.save(user);
    }


    @Override
    public User getUserWithEmail(String email) {
        return userDao.findByEmail(email);
    }


}
