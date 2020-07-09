package com.ilife.zhihu.service;

import com.ilife.zhihu.entity.Activity;
import com.ilife.zhihu.entity.Question;

public interface ZhihuService {
    Question addQuestion(Question question);
    Activity getUserActivity(String zhihuId);
}
