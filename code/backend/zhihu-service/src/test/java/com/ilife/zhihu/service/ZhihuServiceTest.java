package com.ilife.zhihu.service;

import com.alibaba.fastjson.JSON;
import com.ilife.zhihu.entity.*;
import com.ilife.zhihu.repository.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ZhihuServiceTest {

    @Autowired
    ZhihuService zhihuService;
    @MockBean
    UserRepository userRepository;
    @MockBean
    ActivityRepository activityRepository;
    @MockBean
    QuestionRepository questionRepository;
    @MockBean
    ArticleRepository articleRepository;
    @MockBean
    AnswerRepository answerRepository;


    @Test
    void getQuestionById() {
        String questionId = "123";
        Question question = new Question(questionId,"title","content","excerpt",new Timestamp(123),new Timestamp(123),1,null);
        when(questionRepository.findById(questionId)).thenReturn(java.util.Optional.of(question));
        Assertions.assertEquals(question,zhihuService.getQuestionById(questionId));
    }

    @Test
    void getArticleById() {
        String articleId = "123";
        Article article = new Article(articleId,"title","author","content","excerpt","column",new Timestamp(123),null);
        when(articleRepository.findById(articleId)).thenReturn(java.util.Optional.of(article));
        Assertions.assertEquals(article,zhihuService.getArticleById(articleId));
    }

    @Test
    void getAnswerById() {
        String questionId = "345";
        Question question = new Question(questionId,"title","content","excerpt",new Timestamp(123),new Timestamp(123),1,null);

        String answerId = "123";
        Answer answer = new Answer(answerId,question,"author","content","excerpt",new Timestamp(123),new Timestamp(123),1,2);
        when(answerRepository.findById(answerId)).thenReturn(java.util.Optional.of(answer));
        Assertions.assertEquals(answer,zhihuService.getAnswerById(answerId));
    }

    @Test
    void getUserActivity() {
        User user = new User("uid","name","email","phone",1,2,3,-1,null);
        List<Activity> list = new ArrayList<>();
        Activity activity = new Activity("id","123",user,"type","action",new Timestamp(123));
        list.add(activity);
        user.setActivities(list);
        when(userRepository.findByEmail("email")).thenReturn(user);
        Assertions.assertEquals(list,zhihuService.getUserActivity("email"));
    }

    @Test
    void getUserWithEmail() {
        User user = new User("uid","name","email","phone",1,2,3,-1,null);
        when(userRepository.findByEmail("email")).thenReturn(user);
        Assertions.assertEquals(user,zhihuService.getUserWithEmail("email"));
    }

    @Test
    void saveActivitiesFromJsonString() {
        User user = new User("uid","name","email","phone",1,2,3,-1,null);

        System.out.println(activitiesString);
        zhihuService.saveActivitiesFromJsonString(user,activitiesString);
    }

    @Test
    void saveUserFromJsonString() {
        User user = new User("uid","name","email","phone",1,2,3,-1,null);
        zhihuService.saveUserFromJsonString("email@qq.com",JSON.toJSONString(user));
    }

    private static String activitiesString = "[\n" +
            "    {\n" +
            "        \"id\": \"id\",\n" +
            "        \"action_text\": \"\\u8d75\\u65ed\\u9633\\u5173\\u6ce8\\u4e86\\u95ee\\u9898\",\n" +
            "        \"create_time\": 1594622984,\n" +
            "        \"question\": {\n" +
            "            \"id\": \"id\",\n" +
            "            \"answer_count\": 2392,\n" +
            "            \"answers\": [\n" +
            "                {\n" +
            "                    \"id\": \"id\",\n" +
            "                    \"author\": \"MUMA\",\n" +
            "                    \"comment_count\": 983,\n" +
            "                    \"content\": \"<p>\\u4ece\\u5730\\u65b9\\u653f\\u5e9c\\u7ecf\\u6d4e\\u90e8\\u95e8\\u5de5\\u4f5c\\u4eba\\u5458\\u7684\\u89d2\\u5ea6\\u968f\\u4fbf\\u8bf4\\u4e00\\u4e0b\\u611f\\u89c9\\u5427\\uff0c\\u90a3\\u5c31\\u662f\\u56fd\\u571f\\u8d44\\u6e90\\u90e8\\u5bf9\\u8d35\\u5dde\\u771f\\u662f\\u592a\\u504f\\u8892\\u4e86\\u3002</p><p>\\u7edd\\u5927\\u591a\\u6570\\u4eba\\u770b\\u5230\\u72ec\\u5c71\\u53bf\\uff0c\\u53ea\\u770b\\u5230\\u4e86400\\u4ebf\\u5730\\u65b9\\u503a\\uff0c\\u770b\\u5230\\u4e86\\u4ee4\\u4eba\\u532a\\u5937\\u6240\\u601d\\u7684\\u5947\\u89c2\\uff0c\\u4ee5\\u53ca\\u5f53\\u5730\\u653f\\u5e9c\\u8111\\u6d1e\\u5927\\u5f00\\u7684\\u6295\\u8d44\\u8ba1\\u5212\\u3002\\u4f46\\u4f5c\\u4e3a\\u540c\\u6837\\u662f\\u897f\\u90e8\\u7701\\u4efd\\u7ecf\\u6d4e\\u90e8\\u95e8\\u7684\\u7b54\\u4e3b\\u6765\\u8bf4\\uff0c\\u6700\\u5927\\u7684\\u611f\\u89c9\\u5c31\\u662f\\u4ee5\\u72ec\\u5c71\\u53bf\\u4e3a\\u4ee3\\u8868\\u7684\\u8d35\\u5dde\\u5404\\u53bf\\uff0c\\u4ece\\u56fd\\u571f\",\n" +
            "                    \"create_time\": 1594611657,\n" +
            "                    \"excerpt\": \"\\u4ece\\u5730\\u65b9\\u653f\\u5e9c\\u7ecf\\u6d4e\\u90e8\\u95e8\\u5de5\\u4f5c\\u4eba\\u5458\\u7684\\u89d2\\u5ea6\\u968f\\u4fbf\\u8bf4\\u4e00\\u4e0b\\u611f\\u89c9\\u5427\\uff0c\\u90a3\\u5c31\\u662f\\u56fd\\u571f\\u8d44\\u6e90\\u90e8\\u5bf9\\u8d35\\u5dde\\u771f\\u662f\\u592a\\u504f\\u8892\\u4e86\\u3002 \\u7edd\\u5927\\u591a\\u6570\\u4eba\\u770b\\u5230\\u72ec\\u5c71\\u53bf\\uff0c\",\n" +
            "                    \"question\": {\n" +
            "                        \"answer_count\": 0,\n" +
            "                        \"answers\": [],\n" +
            "                        \"content\": \"\",\n" +
            "                        \"create_time\": 0,\n" +
            "                        \"excerpt\": \"\",\n" +
            "                        \"title\": \"\",\n" +
            "                        \"update_time\": 0\n" +
            "                    },\n" +
            "                    \"update_time\": 1594650983,\n" +
            "                    \"voteup_count\": 8060\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": \"id\",\n" +
            "                    \"author\": \"\\u738b\\u5927\\u53ef\",\n" +
            "                    \"comment_count\": 145,\n" +
            "                    \"content\": \"<p>\\u771f\\u00b7\\u7761\\u524d\\u6d88\\u606f\\u5468\\u8fb9\\u8d2d\\u4e70\\u94fe\\u63a5</p><a data-draft-node=\\\"block\\\" data-draft-type=\\\"link-card\\\" href=\\\"https://link.zhihu.com/?target=https%3A//mp.weixin.qq.com/s/xBQv9uYKah3vvxahbkLuFA\\\" data-image=\\\"https://picb.zhimg.com/v2-8d356623ae7176123995ed29ccb03574.jpg\\\" data-image-width=\\\"844\\\" data-image-height=\\\"359\\\" class=\\\" wrap external\\\" target=\\\"_blank\\\" rel=\\\"nofollow noreferrer\\\">\\u6c11\\u56fd\\u98ce\\u60c5\\u8857\\u706b\\u70ed\\u5f00\\u552e\\uff01</a><p class=\\\"ztext-empty-paragraph\\\"><br/></p><figure data-size=\\\"normal\\\"><img src=\\\"https://pic4.zhimg.com/50/v2-8985496385377da0967ecd987a64d9bf_hd.jpg?source=1940ef5c\\\" data-rawwidth=\\\"1920\\\" data-rawheight=\\\"1080\\\" data-size=\\\"normal\\\" class=\\\"origin_image zh-lightbox-thumb\\\" width=\\\"1920\\\" data-original=\\\"https://pic1.zhimg.com/v2-8985496385377da0967ecd987a64d9bf_r.jpg?source=1940ef5c\\\"/><figcaption>\\u4f60\\u77e5\\u9053\\u8fd9\\u4e2a\\u4e8c\\u7ef4\\u7801\\u6211\\u626b\\u5f97\\u591a\\u8d39\\u52b2\\u5417\\uff1f</figcaption></figure><p>\\u6700\\u5927\\u7684\\u611f\\u89e6\\u5c31\\u662f\\uff0c\\u94b1\\uff0c\\u53ea\\u8981\\u591f\\u591a\\uff0c\\u4e0d\\u8bba\\u53d8\\u6210\\u4ec0\\u4e48\\u5f62\\u6001\\u90fd\\u4f1a\\u597d\\u58ee\\u89c2\\u554a\\u3002</p>\",\n" +
            "                    \"create_time\": 1594567454,\n" +
            "                    \"excerpt\": \"\\u771f\\u00b7\\u7761\\u524d\\u6d88\\u606f\\u5468\\u8fb9\\u8d2d\\u4e70\\u94fe\\u63a5 \\u6c11\\u56fd\\u98ce\\u60c5\\u8857\\u706b\\u70ed\\u5f00\\u552e\\uff01 [\\u56fe\\u7247] \\u6700\\u5927\\u7684\\u611f\\u89e6\\u5c31\\u662f\\uff0c\\u94b1\\uff0c\\u53ea\\u8981\\u591f\\u591a\\uff0c\\u4e0d\\u8bba\\u53d8\\u6210\\u4ec0\\u4e48\\u5f62\\u6001\\u90fd\\u4f1a\\u597d\\u58ee\\u89c2\\u554a\\u3002\",\n" +
            "                    \"question\": {\n" +
            "                        \"answer_count\": 0,\n" +
            "                        \"answers\": [],\n" +
            "                        \"content\": \"\",\n" +
            "                        \"create_time\": 0,\n" +
            "                        \"excerpt\": \"\",\n" +
            "                        \"title\": \"\",\n" +
            "                        \"update_time\": 0\n" +
            "                    },\n" +
            "                    \"update_time\": 1594641433,\n" +
            "                    \"voteup_count\": 2615\n" +
            "                }\n" +
            "            ],\n" +
            "            \"content\": \"<p>\\u6211\\u5355\\u7eaf\\u60f3\\u542c\\u542c\\u5404\\u4f4d\\u9ad8\\u89c1\\uff0c\\u5b9e\\u5728\\u662f\\u592a\\u8fc7\\u4e8e\\u9707\\u64bc\\uff0c\\u90a3\\u4e9b\\u5efa\\u7b51\\uff0c\\u8bbe\\u8ba1\\u7684\\u662f\\u771f\\u7684\\u9b54\\u5e7b</p>\",\n" +
            "            \"create_time\": 1594565034,\n" +
            "            \"excerpt\": \"\\u6211\\u5355\\u7eaf\\u60f3\\u542c\\u542c\\u5404\\u4f4d\\u9ad8\\u89c1\\uff0c\\u5b9e\\u5728\\u662f\\u592a\\u8fc7\\u4e8e\\u9707\\u64bc\\uff0c\\u90a3\\u4e9b\\u5efa\\u7b51\\uff0c\\u8bbe\\u8ba1\\u7684\\u662f\\u771f\\u7684\\u9b54\\u5e7b\",\n" +
            "            \"title\": \"\\u770b\\u4e86\\u7761\\u524d\\u6d88\\u606f 140 \\u671f\\u300c\\u4eb2\\u773c\\u770b\\u770b\\u72ec\\u5c71\\u53bf\\u600e\\u4e48\\u70e7\\u6389 400 \\u4ebf\\u300d\\uff0c\\u5927\\u5bb6\\u4ec0\\u4e48\\u611f\\u89c9\\uff1f\",\n" +
            "            \"update_time\": 1594605875\n" +
            "        },\n" +
            "        \"type\": \"FOLLOW_QUESTION\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": \"id\",\n" +
            "        \"action_text\": \"\\u8d75\\u65ed\\u9633\\u8d5e\\u540c\\u4e86\\u56de\\u7b54\",\n" +
            "        \"answer\": {\n" +
            "            \"id\": \"id\",\n" +
            "            \"author\": \"\\u98ce\\u4fe1\\u5b50\",\n" +
            "            \"comment_count\": 951,\n" +
            "            \"content\": \"<p>\\u4e2d\\u79d1\\u9662\\u6606\\u660e\\u52a8\\u7269\\u6240\\u5df2\\u6210\\u7acb\\u8c03\\u67e5\\u7ec4\\u8c03\\u67e5\\uff0c\\u95ee\\u9898\\u5728\\u77ed\\u77ed\\u4e00\\u5929\\u5185\\u5c31\\u80fd\\u5f15\\u8d77\\u8fd9\\u4e48\\u5927\\u7684\\u53cd\\u54cd\\uff0c\\u5e0c\\u671b\\u672a\\u6765\\u7684\\u8d70\\u5411\\u4e0d\\u662f\\u6252\\u76ae\\u548c\\u553e\\u9a82\\uff0c\\u800c\\u662f\\u80fd\\u4e3a\\u6211\\u4eec\\u7684\\u9752\\u5c11\\u5e74\\u600e\\u4e48\\u771f\\u6b63\\u505a\\u300c\\u79d1\\u6280\\u521b\\u65b0\\u300d\\u63d0\\u4f9b\\u4e00\\u70b9\\u70b9\\u601d\\u8003\\u3002\\u66f4\\u65b0\\u89c1\\u56de\\u7b54\\u6700\\u5e95 \\u2193\\u2193\",\n" +
            "            \"create_time\": 1594533574,\n" +
            "            \"excerpt\": \"\\u4e2d\\u79d1\\u9662\\u6606\\u660e\\u52a8\\u7269\\u6240\\u5df2\\u6210\\u7acb\\u8c03\\u67e5\\u7ec4\\u8c03\\u67e5\\uff0c\\u95ee\\u9898\\u5728\\u77ed\\u77ed\\u4e00\\u5929\\u5185\\u5c31\\u80fd\\u5f15\",\n" +
            "            \"question\": {\n" +
            "                \"id\": \"id\",\n" +
            "                \"answer_count\": 5614,\n" +
            "                \"answers\": [],\n" +
            "                \"content\": \"<p>\\u5168\\u56fd\\u9752\\u5c11\\u5e74\\u79d1\\u6280\\u521b\\u65b0\\u5927\\u8d5b\\u5b98\\u7f51\\u5728\\u7ebf\\u5c55\\u5385\\uff1a</p><a data-draft-node=\\\"block\\\" data-draft-type=\\\"link-card\\\" href=\\\"https://link.zhihu.com/?target=http%3A//castic.xiaoxiaotong.org/Query/SubjectDetail.aspx%3FSubjectID%3D77240\\\" data-image=\\\"https://pic1.zhimg.com/v2-70c565ccfe5900419c7d3b64fe3ffb0e_120x160.jpg\\\" data-image-width=\\\"480\\\" data-image-height=\\\"640\\\" class=\\\" wrap external\\\" target=\\\"_blank\\\" rel=\\\"nofollow noreferrer\\\">\\u5728\\u7ebf\\u5c55\\u5385</a><figure data-size=\\\"normal\\\"><img src=\\\"https://picb.zhimg.com/v2-4829b394045139563fd455fe5fb5deed_b.png\\\" data-rawwidth=\\\"1280\\\" data-rawheight=\\\"1032\\\" data-size=\\\"normal\\\" data-caption=\\\"\\\" class=\\\"origin_image zh-lightbox-thumb\\\" width=\\\"1280\\\" data-original=\\\"https://picb.zhimg.com/v2-4829b394045139563fd455fe5fb5deed_r.jpg\\\"/></figure><figure data-size=\\\"normal\\\"><img src=\\\"https://pic3.zhimg.com/v2-300eb26988ffcd6f1be946d251d72d5f_b.png\\\" data-rawwidth=\\\"1440\\\" data-rawheight=\\\"1080\\\" data-size=\\\"normal\\\" data-caption=\\\"\\\" class=\\\"origin_image zh-lightbox-thumb\\\" width=\\\"1440\\\" data-original=\\\"https://pic3.zhimg.com/v2-300eb26988ffcd6f1be946d251d72d5f_r.jpg\\\"/></figure><figure data-size=\\\"normal\\\"><img src=\\\"https://picb.zhimg.com/v2-094a7873910604a01ced554b9e3c3c25_b.png\\\" data-rawwidth=\\\"1440\\\" data-rawheight=\\\"1080\\\" data-size=\\\"normal\\\" data-caption=\\\"\\\" class=\\\"origin_image zh-lightbox-thumb\\\" width=\\\"1440\\\" data-original=\\\"https://picb.zhimg.com/v2-094a7873910604a01ced554b9e3c3c25_r.jpg\\\"/></figure><p>\\u9ad8\\u539f\\u54fa\\u4e73\\u52a8\\u7269\\uff08\\u5305\\u62ec\\u4eba\\u7c7b\\uff09\\u7684\\u673a\\u4f53\\u5bf9\\u9ad8\\u539f\\u9002\\u5e94\\u4e3b\\u8981\\u8868\\u73b0\\u4e4b\\u4e00\\u5c31\\u662f\\u4f4e\\u6c27\\u9002\\u5e94\\uff0c\\u800c\\u4f4e\\u6c27\\u5728\\u4eba\\u7c7b\\u75be\\u75c5\\u5305\\u62ec\\u5b9e\\u4f53\\u7624\\u4e2d\\u4e5f\\u5e38\\u53d1\\u751f\\u3002\\u5229\\u7528\\u9ad8\\u539f\\u9002\\u5e94\\u4e0e\\u80bf\\u7624\\u7ec6\\u80de\\u9002\\u5e94\\u7684\\u76f8\\u4f3c\\u6027\\uff0c\\u672c\\u9879\\u76ee\\u524d\\u671f\\u5229\\u7528\\u9057\\u4f20\\u5b66\\u6bd4\\u8f83\\u5206\\u6790\\u4e86\\u9ad8\\u539f\\u5bb6\\u517b\\u54fa\\u4e73\\u52a8\\u7269\\u548c\\u5bf9\\u5e94\\u5e73\\u539f\\u7269\\u79cd\\u7684\\u57fa\\u56e0\\u7ec4\\u548c\\u8f6c\\u5f55\\u7ec4\\uff0c\\u53d1\\u73b0\\u4e86\\u9ad8\\u539f\\u54fa\\u4e73\\u52a8\\u7269\\u4f4e\\u6c27\\u9002\\u5e94\\u53d7\\u9009\\u62e9\\u7684\\u5173\\u952e\\u7a81\\u53d8\\u57fa\\u56e0C10orf67\\uff0c\\u5e76\\u6784\\u5efa\\u4e86C10orf67\\u57fa\\u56e0\\u6572\\u9664\\u5c0f\\u9f20\\uff0c\\u901a\\u8fc7\\u7ec6\\u80de\\u751f\\u7269\\u5b66\\uff0c\\u751f\\u7269\\u5316\\u5b66\\u3001\\u52a8\\u7269\\u6a21\\u578b\\u3001\\u4e34\\u5e8a\\u6837\\u672c\\u5206\\u6790\\u7b49\\u65b9\\u9762\\u5bf9C10orf67\\u5728\\u7ed3\\u76f4\\u80a0\\u764c\\u53d1\\u751f\\u53d1\\u5c55\\u4e2d\\u7684\\u4f5c\\u7528\\u8fdb\\u884c\\u89e3\\u6790\\uff0c\\u53d1\\u73b0C10orf67\\u5728\\u7ed3\\u76f4\\u80a0\\u764c\\u4e2d\\u9ad8\\u8868\\u8fbe\\uff0c\\u6572\\u4f4e\\u5176\\u8868\\u8fbe\\u53ef\\u4ee5\\u663e\\u8457\\u6291\\u5236\\u7ec6\\u80de\\u7684\\u589e\\u6b96\\uff0c\\u5c06\\u7ec6\\u80de\\u963b\\u6ede\\u5728G2/M\\u671f\\u3002\\u8fdb\\u4e00\\u6b65\\u7684\\u7814\\u7a76\\u8868\\u660eC10orf67\\u53ef\\u4ee5\\u8c03\\u8282\\u7ed3\\u76f4\\u80a0\\u764c\\u7ec6\\u80de\\u5bf9\\u5316\\u7597\\u836f\\u7269\\u7684\\u654f\\u611f\\u6027\\u3002\\u56e0\\u6b64\\uff0c\\u5bf9C10orf67\\u5728\\u7ed3\\u76f4\\u80a0\\u764c\\u4e2d\\u7684\\u529f\\u80fd\\u89e3\\u6790\\uff0c\\u6709\\u671b\\u4e3a\\u7ed3\\u76f4\\u80a0\\u764c\\u7684\\u8bca\\u65ad\\u548c\\u6cbb\\u7597\\u63d0\\u4f9b\\u65b0\\u7684\\u751f\\u7269\\u6807\\u5fd7\\u7269\\u548c\\u836f\\u7269\\u9776\\u70b9\\u3002</p>\",\n" +
            "                \"create_time\": 1594476081,\n" +
            "                \"excerpt\": \"\\u5168\\u56fd\\u9752\\u5c11\\u5e74\\u79d1\\u6280\\u521b\\u65b0\\u5927\\u8d5b\\u5b98\\u7f51\\u5728\\u7ebf\\u5c55\\u5385\\uff1a \\u5728\\u7ebf\\u5c55\\u5385 [\\u56fe\\u7247] [\\u56fe\\u7247] [\\u56fe\\u7247] \\u9ad8\\u539f\\u54fa\\u4e73\\u52a8\\u7269\\uff08\\u5305\\u62ec\\u4eba\\u7c7b\\uff09\\u7684\\u673a\\u4f53\\u5bf9\\u9ad8\\u539f\\u9002\\u5e94\\u4e3b\\u8981\\u8868\\u73b0\\u4e4b\\u4e00\\u5c31\\u662f\\u4f4e\\u6c27\\u9002\\u5e94\\uff0c\\u800c\\u4f4e\\u6c27\\u5728\\u4eba\\u7c7b\\u75be\\u75c5\\u5305\\u62ec\\u5b9e\\u4f53\\u7624\\u4e2d\\u4e5f\\u5e38\\u53d1\\u751f\\u3002\\u5229\\u7528\\u9ad8\\u539f\\u9002\\u5e94\\u4e0e\\u80bf\\u7624\\u7ec6\\u80de\\u9002\\u5e94\\u7684\\u76f8\\u4f3c\\u6027\\uff0c\\u672c\\u9879\\u76ee\\u524d\\u671f\\u5229\\u7528\\u9057\\u4f20\\u5b66\\u6bd4\\u8f83\\u5206\\u6790\\u4e86\\u9ad8\\u539f\\u5bb6\\u517b\\u54fa\\u4e73\\u52a8\\u7269\\u548c\\u5bf9\\u5e94\\u5e73\\u539f\\u7269\\u79cd\\u7684\\u57fa\\u56e0\\u7ec4\\u548c\\u8f6c\\u5f55\\u7ec4\\uff0c\\u53d1\\u73b0\\u4e86\\u9ad8\\u539f\\u54fa\\u4e73\\u52a8\\u7269\\u4f4e\\u6c27\\u9002\\u5e94\\u53d7\\u9009\\u62e9\\u7684\\u5173\\u952e\\u7a81\\u53d8\\u57fa\\u56e0C10orf67\\uff0c\\u5e76\\u6784\\u5efa\\u4e86C10orf67\\u57fa\\u56e0\\u6572\\u9664\\u5c0f\\u9f20\\uff0c\\u901a\\u8fc7\\u7ec6\\u80de\\u751f\\u7269\\u5b66\\uff0c\\u751f\\u7269\\u5316\\u5b66\\u3001\\u52a8\\u7269\\u6a21\\u578b\\u3001\\u4e34\\u5e8a\\u6837\\u672c\\u5206\\u6790\\u2026\",\n" +
            "                \"title\": \"\\u5982\\u4f55\\u8bc4\\u4ef7\\u6606\\u660e\\u516d\\u5e74\\u7ea7\\u5c0f\\u5b66\\u751f\\u9648\\u7075\\u77f3\\u51ed\\u501f\\u7ed3\\u76f4\\u80a0\\u764c\\u57fa\\u56e0\\u6572\\u9664\\u7814\\u7a76\\u53c2\\u52a0\\u5168\\u56fd\\u9752\\u5c11\\u5e74\\u79d1\\u6280\\u521b\\u65b0\\u5927\\u8d5b\\uff0c\\u4ed6\\u7684\\u79d1\\u7814\\u80fd\\u529b\\u5982\\u4f55\\uff1f\",\n" +
            "                \"update_time\": 1594541028\n" +
            "            },\n" +
            "            \"update_time\": 1594623797,\n" +
            "            \"voteup_count\": 15285\n" +
            "        },\n" +
            "        \"create_time\": 1594597167,\n" +
            "        \"type\": \"VOTEUP_ANSWER\"\n" +
            "    },{\n" +
            "        \"id\": \"id\",\n" +
            "        \"action_text\": \"\\u8d75\\u65ed\\u9633\\u8d5e\\u540c\\u4e86\\u6587\\u7ae0\",\n" +
            "        \"article\": {\n" +
            "            \"id\": \"id\",\n" +
            "            \"author\": \"\\u91cf\\u5b50\\u4f4d\",\n" +
            "            \"column_name\": \"\\u91cf\\u5b50\\u4f4d\",\n" +
            "            \"content\": \"<blockquote>\\u90ed\\u4e00\\u749e \\u53d1\\u81ea \\u4e91\\u51f9\\u975e\\u5bfa<br/>\\u91cf\\u5b50\\u4f4d \\u62a5\\u9053 | \\u516c\\u4f17\\u53f7 QbitAI</blockquote><p>CNN\\u662f\\u4ec0\\u4e48\\uff1f\\u7f8e\\u56fd\\u6709\\u7ebf\\u7535\\u89c6\\u65b0\\u95fb\\u7f51\\u5417\\uff1f</p><p>\\u6bcf\\u4e00\\u4e2a\\u5bf9AI\\u62b1\\u6709\\u61a7\\u61ac\\u7684\\u5c0f\\u767d\\uff0c\\u5728\\u5f00\\u59cb\\u7684\\u65f6\\u5019\\u90fd\\u4f1a\\u9047\\u5230CNN\\uff08\\u5377\\u79ef\\u795e\\u7ecf\\u7f51\\u7edc\\uff09\\u8fd9\\u4e2a\\u8bcd\\u3002</p><p>\\u4f46\\u6bcf\\u6b21\\uff0c\\u5f53\\u5c0f\\u767d\\u4eec\\u60f3\\u4e86\\u89e3CNN\\u5230\\u5e95\\u662f\\u600e\\u4e48\\u56de\\u4e8b\\uff0c\\u4e3a\\u4ec0\\u4e48\\u5c31\\u80fd\\u806a\\u660e\\u7684\\u8bc6\\u522b\\u4eba\\u8138\\u3001\\u542c\\u8fa8\\u58f0\\u97f3\\u7684\\u65f6\\u5019\\uff0c\\u5c31\\u61f5\\u4e86\\uff0c\\u53ea\\u597d\\u7406\\u89e3\\u4e3a\\u7384\\u5b66\\uff1a</p><p class=\\\"ztext-empty-paragraph\\\"><br/></p><figure data-size=\\\"normal\\\"><noscript><img src=\\\"https://pic2.zhimg.com/v2-d106689a5fd2f195768822a4b21e31da_b.jpg\\\" data-caption=\\\"\\\" data-size=\\\"normal\\\" data-rawwidth=\\\"640\\\" data-rawheight=\\\"345\\\" class=\\\"origin_image zh-lightbox-thumb\\\" width=\\\"640\\\" data-original=\\\"https://pic2.zhimg.com/v2-d106689a5fd2f195768822a4b21e31da_r.jpg\\\"/></noscript><img src=\\\"data:image/svg+xml;utf8,&lt;svg xmlns=&#39;http://www.w3.org/2000/svg&#39; width=&#39;640&#39; height=&#39;345&#39;&gt;&lt;/svg&gt;\\\" data-caption=\\\"\\\" data-size=\\\"normal\\\" data-rawwidth=\\\"640\\\" data-rawheight=\\\"345\\\" class=\\\"origin_image zh-lightbox-thumb lazy\\\" width=\\\"640\\\" data-original=\\\"https://pic2.zhimg.com/v2-d106689a5fd2f195768822a4b21e31da_r.jpg\\\" data-actualsrc=\\\"https://pic2.zhimg.com/v2-d106689a5fd2f195768822a4b21e31da_b.jpg\\\"/></figure><p class=\\\"ztext-empty-paragraph\\\"><br/></p><p>\\u597d\\u5427\\uff0c\\u7ef4\\u57fa\\u767e\\u79d1\\u89e3\\u51b3\\u4e0d\\u4e86\\u7684\\u95ee\\u9898\\uff0c\\u6709\\u4eba\\u7ed9\\u89e3\\u51b3\\u4e86\\u3002</p><p class=\\\"ztext-empty-paragraph\\\"><br/></p><figure data-size=\\\"normal\\\"><noscript><img src=\\\"https://pic1.zhimg.com/v2-6922efe1d930a5b641d62ec70f80f9dd_b.jpg\\\" data-caption=\\\"\\\" data-size=\\\"normal\\\" data-rawwidth=\\\"640\\\" data-rawheight=\\\"325\\\" class=\\\"origin_image zh-lightbox-thumb\\\" width=\\\"640\\\" data-original=\\\"https://pic1.zhimg.com/v2-6922efe1d930a5b641d62ec70f80f9dd_r.jpg\\\"/></noscript><img src=\\\"data:image/svg+xml;utf8,&lt;svg xmlns=&#39;http://www.w3.org/2000/svg&#39; width=&#39;640&#39; height=&#39;325&#39;&gt;&lt;/svg&gt;\\\" data-caption=\\\"\\\" data-size=\\\"normal\\\" data-rawwidth=\\\"640\\\" data-rawheight=\\\"325\\\" class=\\\"origin_image zh-lightbox-thumb lazy\\\" width=\\\"640\\\" data-original=\\\"https://pic1.zhimg.com/v2-6922efe1d930a5b641d62ec70f80f9dd_r.jpg\\\" data-actualsrc=\\\"https://pic1.zhimg.com/v2-6922efe1d930a5b641d62ec70f80f9dd_b.jpg\\\"/></figure><p class=\\\"ztext-empty-paragraph\\\"><br/></p><p>\\u8fd9\\u4e2a\\u540d\\u53eb<b>CNN\\u89e3\\u91ca\\u5668</b>\",\n" +
            "            \"excerpt\": \"[\\u56fe\\u7247] \\u90ed\\u4e00\\u749e \\u53d1\\u81ea \\u4e91\\u51f9\\u975e\\u5bfa \\u91cf\\u5b50\\u4f4d \\u62a5\\u9053 | \\u516c\\u4f17\\u53f7 QbitAICNN\\u662f\\u4ec0\\u4e48\\uff1f\\u7f8e\\u56fd\\u6709\\u7ebf\\u7535\\u89c6\\u65b0\\u95fb\\u7f51\\u5417\\uff1f\\u6bcf\\u4e00\\u4e2a\\u5bf9AI\\u62b1\\u6709\\u61a7\\u61ac\\u7684\\u5c0f\\u767d\\uff0c\\u5728\\u5f00\\u59cb\\u7684\\u65f6\\u5019\\u90fd\\u4f1a\\u9047\\u5230CNN\\uff08\\u5377\\u79ef\\u795e\\u7ecf\\u7f51\\u7edc\\uff09\\u8fd9\\u4e2a\\u8bcd\\u3002\\u4f46\\u6bcf\\u6b21\\uff0c\\u5f53\\u5c0f\\u767d\\u4eec\\u60f3\\u4e86\\u89e3CNN\\u5230\\u5e95\\u662f\\u600e\\u4e48\\u56de\\u4e8b\\uff0c\\u4e3a\\u4ec0\\u4e48\\u5c31\\u80fd\\u806a\\u660e\\u7684\\u8bc6\\u522b\\u4eba\\u8138\\u3001\\u542c\\u8fa8\\u58f0\\u97f3\\u7684\\u65f6\\u5019\\uff0c\\u2026\",\n" +
            "            \"image_url\": \"https://pic1.zhimg.com/v2-225dfd3cd04850113a98cb00152b8d2a_720w.jpg?source=172ae18b\",\n" +
            "            \"title\": \"\\u4e00\\u4f4d\\u4e2d\\u56fd\\u535a\\u58eb\\u628a\\u6574\\u4e2aCNN\\u90fd\\u7ed9\\u53ef\\u89c6\\u5316\\u4e86\\uff0c\\u53ef\\u4ea4\\u4e92\\u6709\\u7ec6\\u8282\\uff0c\\u6bcf\\u6b21\\u5377\\u79efReLU\\u6c60\\u5316\\u90fd\\u6e05\\u6e05\\u695a\\u695a\",\n" +
            "            \"update_time\": 1588414396\n" +
            "        },\n" +
            "        \"create_time\": 1588470466,\n" +
            "        \"type\": \"VOTEUP_ARTICLE\"\n" +
            "    }\n" +
            "]";

}