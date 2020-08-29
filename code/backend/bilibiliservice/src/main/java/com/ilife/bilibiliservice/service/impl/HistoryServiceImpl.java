package com.ilife.bilibiliservice.service.impl;

import com.google.inject.internal.asm.$Opcodes;
import com.ilife.bilibiliservice.dao.HistoryDao;
import com.ilife.bilibiliservice.entity.history;
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


    Map<String,Integer> map = new HashMap<>();

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
        int k = -1;
        if(s.equals("MAD·AMV")) k = 24;
        else if(s.equals("MMD·3D")) k = 25;
        else if(s.equals( "短片·手书·配音")) k = 47;
        else if(s.equals("特摄")) k = 86;
        else if(s.equals("综合")) k = 27;
        else if(s.equals("连载动画")) k = 33;
        else if(s.equals("完结动画")) k = 32;
        else if(s.equals("资讯"))k = 51;
        else if(s.equals("官方延伸")) k =152;
        else if(s.equals("国产动画")) k = 153;
        else if(s.equals("国产原创相关")) k = 168;
        else if(s.equals("布袋戏")) k = 169;
        else if(s.equals("动态漫·广播剧")) k = 195;
        else if(s.equals("资讯")) k = 170;
        else if(s.equals("原创音乐")) k = 28;
        else if(s.equals("翻唱")) k = 31;
        else if(s.equals("VOCALOID·UTAU")) k = 30;
        else if(s.equals("电音")) k = 194;
        else if(s.equals("演奏")) k = 59;
        else if(s.equals("MV")) k = 193;
        else if(s.equals("音乐现场")) k = 29;
        else if(s.equals("音乐综合")) k = 130;
        else if(s.equals("宅舞")) k = 20;
        else if(s.equals("街舞")) k = 198;
        else if(s.equals("明星舞蹈")) k = 199;
        else if(s.equals("中国舞")) k = 200;
        else if(s.equals("舞蹈综合")) k = 154;
        else if(s.equals("舞蹈教程")) k = 156;
        else if(s.equals("单机游戏")) k = 17;
        else if(s.equals("电子竞技")) k = 171;
        else if(s.equals("手机游戏")) k = 172;
        else if(s.equals("网络游戏")) k = 65;
        else if(s.equals("桌游棋牌")) k = 173;
        else if(s.equals("GMV")) k = 121;
        else if(s.equals("音游")) k = 136;
        else if(s.equals("Mugen")) k = 19;
        else if(s.equals("科学科普")) k = 201;
        else if(s.equals("社科人文")) k = 124;
        else if(s.equals("财经")) k = 207;
        else if(s.equals("校园学习")) k = 208;
        else if(s.equals("职业职场")) k = 209;
        else if(s.equals("野生技术协会")) k =122;
        else if(s.equals("手机平板")) k = 95;
        else if(s.equals("电脑装机")) k = 189;
        else if(s.equals("摄影摄像")) k = 190;
        else if(s.equals("影音智能")) k = 191;
        else if(s.equals("搞笑")) k = 138;
        else if(s.equals("日常")) k = 21;
        else if(s.equals("美食圈")) k = 76;
        else if(s.equals("动物园")) k = 75;
        else if(s.equals("手工")) k = 161;
        else if(s.equals("绘画")) k = 162;
        else if(s.equals("运动")) k = 163;
        else if(s.equals("汽车")) k =176;
        else if(s.equals("其他")) k = 174;
        else if(s.equals("鬼畜调教")) k = 22;
        else if(s.equals("音MAD")) k = 26;
        else if(s.equals("人力VOCALOID")) k =126;
        else if(s.equals("教程演示")) k = 127;
        else if(s.equals("美妆")) k = 157;
        else if(s.equals("服饰")) k = 158;
        else if(s.equals("健身")) k = 164;
        else if(s.equals("T台")) k = 159;
        else if(s.equals("风尚标")) k = 192;
        else if(s.equals("热点"))  k = 203;
        else if(s.equals("环球")) k = 204;
        else if(s.equals("社会")) k = 205;
        else if(s.equals("综艺")) k = 71;
        else if(s.equals("明星")) k = 137;
        else if(s.equals("Korea相关")) k = 131;
        else if(s.equals("影视杂谈")) k = 182;
        else if(s.equals("影视剪辑")) k = 183;
        else if(s.equals("短片")) k = 85;
        else if(s.equals("预告·资讯")) k = 184;
        else if(s.equals("人文·历史")) k =37;
        else if(s.equals("科学·探索·自然")) k =178;
        else if(s.equals("军事")) k = 179;
        else if(s.equals("社会·美食·旅行")) k = 180;
        else if(s.equals("华语电影")) k = 147;
        else if(s.equals("欧美电影")) k = 145;
        else if(s.equals("日本电影")) k = 146;
        else if(s.equals("其他国家")) k = 83;
        else if(s.equals("国产剧")) k = 185;
        else if(s.equals("海外剧"))  k = 187;
        return k;
    }
}
