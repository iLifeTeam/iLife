package com.ilife.bilibiliservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.inject.internal.asm.$Opcodes;
import com.ilife.bilibiliservice.dao.HistoryDao;
import com.ilife.bilibiliservice.entity.history;
import com.ilife.bilibiliservice.repository.HistoryRepository;
import com.ilife.bilibiliservice.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryDao historyDao;
    private Map<String,Integer> map = new HashMap<String, Integer>();

    public HistoryServiceImpl(){
        map.put("MAD·AMV",24);
        map.put("MMD·3D",25);
        map.put( "短片·手书·配音",47);
        map.put("特摄",86);
        map.put("综合",27);
        map.put("连载动画",33);
        map.put("完结动画",32);
        map.put("资讯",51);
        map.put("官方延伸",152);
        map.put("国产动画",153);
        map.put("国产原创相关",168);
        map.put("布袋戏",169);
        map.put("动态漫·广播剧",195);
        map.put("资讯",170);
        map.put("原创音乐",28);
        map.put("翻唱",31);
        map.put("VOCALOID·UTAU",30);
        map.put("电音",194);
        map.put("演奏",59);
        map.put("MV",193);
        map.put("音乐现场",29);
        map.put("音乐综合",130);
        map.put("宅舞",20);
        map.put("街舞",198);
        map.put("明星舞蹈",199);
        map.put("中国舞",200);
        map.put("舞蹈综合",154);
        map.put("舞蹈教程",156);
        map.put("单机游戏",17);
        map.put("电子竞技",171);
        map.put("手机游戏",172);
        map.put("网络游戏",65);
        map.put("桌游棋牌",173);
        map.put("GMV",121);
        map.put("音游",136);
        map.put("Mugen",19);
        map.put("科学科普",201);
        map.put("社科人文",124);
        map.put("财经",207);
        map.put("校园学习",208);
        map.put("职业职场",209);
        map.put("野生技术协会",122);
        map.put("手机平板",95);
        map.put("电脑装机",189);
        map.put("摄影摄像",190);
        map.put("影音智能",191);
        map.put("搞笑",138);
        map.put("日常",21);
        map.put("美食圈",76);
        map.put("动物园",75);
        map.put("手工",161);
        map.put("绘画",162);
        map.put("运动",163);
        map.put("汽车",176);
        map.put("其他",174);
        map.put("鬼畜调教",22);
        map.put("音MAD",26);
        map.put("人力VOCALOID",126);
        map.put("教程演示",127);
        map.put("美妆",157);
        map.put("服饰",158);
        map.put("健身",164);
        map.put("T台",159);
        map.put("风尚标",192);
        map.put("热点",203);
        map.put("环球",204);
        map.put("社会",205);
        map.put("综艺",71);
        map.put("明星",137);
        map.put("Korea相关",131);
        map.put("影视杂谈",182);
        map.put("影视剪辑",183);
        map.put("短片",85);
        map.put("预告·资讯",184);
        map.put("人文·历史",37);
        map.put("科学·探索·自然",178);
        map.put("军事",179);
        map.put("社会·美食·旅行",180);
        map.put("华语电影",147);
        map.put("欧美电影",145);
        map.put("日本电影",146);
        map.put("其他国家",83);
        map.put("国产剧",185);
        map.put("海外剧",184);
    }



    public List<history> findAllByMid(Long mid){
        return historyDao.findAllByMid(mid);
    }
    public Page<history> findAllByMid(Long mid, Pageable pageable)
    {
        return historyDao.findAllByMid(mid,pageable);
    }
    public List<String> getFavoriteTag(Long id){
        return historyDao.getFavoriteTag(id);
    }
    public List<Long> getFavoriteUp(Long id){
        return historyDao.getFavoriteUp(id);
    }
    public int getFavoriteTagid(String s){
        if (map.get(s)!=null) return map.get(s);
        else return -1;
    }
}
