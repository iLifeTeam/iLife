package com.ilife.weiboservice.service.serviceimpl;

import com.ilife.weiboservice.dao.UserDao;
import com.ilife.weiboservice.dao.WeiboDao;
import com.ilife.weiboservice.entity.Statistics;
import com.ilife.weiboservice.entity.Weibo;
import com.ilife.weiboservice.service.WeiboService;
import org.assertj.core.error.ShouldBeAfterYear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.lang.Integer.parseInt;

@Service
public class WeiboServiceImpl implements WeiboService {

    private SimpleDateFormat day = new SimpleDateFormat("dd");
    private SimpleDateFormat year = new SimpleDateFormat("yyyy");
    private SimpleDateFormat month = new SimpleDateFormat("mm");
    @Autowired
    private WeiboDao weiboDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<Weibo> findAllByUid(Long uid) {
        return weiboDao.findAllByUid(uid);
    }

    @Override
    public ResponseEntity<?> deleteByUid(Long uid) {
        if (userDao.findAllById(uid) == null) {
            System.out.println("user " + uid.toString() + " not exist");
            return ResponseEntity.status(501).body("user " + uid.toString() + " not exist");
        }
        weiboDao.deleteByUid(uid);
        return new ResponseEntity<>("delete all Weibos of " + uid.toString(), HttpStatus.OK);
    }

    @Override
    public Weibo findById(String id) {
        return weiboDao.findById(id);
    }


    @Override
    public ResponseEntity<?> deleteById(String id) {
        if (weiboDao.findById(id) == null)
            return ResponseEntity.status(501).body("Weibo id " + id + " not exists");
        weiboDao.deleteById(id);
        return ResponseEntity.ok("delete Weibo " + id);
    }

    @Override
    public ResponseEntity<?> save(Weibo weibo) {
        if (findById(weibo.getId()) != null) {
            return ResponseEntity.status(501).body("Weibo id " + weibo.getId() + " already exists");
        } else {
            Weibo _weibo = weiboDao.save(weibo);
            return ResponseEntity.ok().body(_weibo.toString());
        }
    }

    @Override
    public Statistics getStats(Long uid,Date startDate,Date endDate){
        Timestamp startTime = new Timestamp(startDate.getTime()); //2013-01-14 22:45:36.484
        Timestamp endTime = new Timestamp(endDate.getTime()); //2013-01-14 22:45:36.484
        List<Weibo> weiboList = weiboDao.findLimits(uid,startTime,endTime);
        float avgUp=0,avgRt=0,avgCm=0,avgWb=0;
        int maxUp,allUp,maxRt,allRt,maxCm,allCm,preTime,maxWb,allWb;
        maxUp=allUp=maxRt=allRt=maxCm=allCm=preTime=maxWb=allWb=0;
        String maxUpWb="",maxRtWb="",maxCmWb="";
        Timestamp maxDate;

        //no data
        if(weiboList.size()==0){
            return new Statistics(avgUp,maxUp,allUp,maxUpWb,avgRt,maxRt,allRt,maxRtWb,avgCm,maxCm,allCm,maxCmWb,avgWb,maxWb,allWb,null);
        }

        for (Weibo weibo : weiboList){
            allUp+=weibo.getUp_num();
            allRt+=weibo.getRetweet_num();
            allCm+=weibo.getComment_count();
            if(maxCm<=weibo.getComment_count()){
                maxCm=weibo.getComment_count();
                maxCmWb=weibo.getContent();
            }
            if(maxRt<=weibo.getRetweet_num()){
                maxRt=weibo.getRetweet_num();
                maxRtWb=weibo.getContent();
            }
            if(maxUp<=weibo.getUp_num()) {
                maxUp = weibo.getUp_num();
                maxUpWb = weibo.getContent();
            }
        }
        for(int i=0;i<weiboList.size();++i){
            boolean flag=false;
            for(int j=0;j<weiboList.size()-i-1;++j){
                Timestamp previous = weiboList.get(j).getPublish_time();
                Timestamp next = weiboList.get(j+1).getPublish_time();
                if(previous.after(next)){
                    flag=true;
                    Weibo tmpWeibo = weiboList.get(j);
                    weiboList.set(j,weiboList.get(j+1));
                    weiboList.set(j+1,tmpWeibo);
                }
            }
            if(!flag) break;
        }
        int _cur=0;
        int _lastDay= parseInt(day.format(weiboList.get(0).getPublish_time()));
        maxDate = weiboList.get(0).getPublish_time();
        for (Weibo weibo : weiboList){
            int _curDay = parseInt(day.format(weibo.getPublish_time()));
            if(_curDay==_lastDay){
                _cur++;
                if(_cur>maxWb){
                    maxWb = _cur;
                    maxDate = weibo.getPublish_time();
                }
            }else{
                _cur = 1;
            }
            _lastDay=_curDay;
        }
        avgUp=(float)allUp/weiboList.size();
        avgCm=(float)allCm/weiboList.size();
        avgRt=(float)allRt/weiboList.size();
        int period = 365*(parseInt(year.format(endTime))-parseInt(year.format(startTime)))+30*(parseInt(month.format(endTime))-parseInt(month.format(startTime)))+parseInt(day.format(endTime))-parseInt(day.format(startTime));
        avgWb=(float)weiboList.size()/period;
        allWb = weiboList.size();
        return new Statistics(avgUp,maxUp,allUp,maxUpWb,avgRt,maxRt,allRt,maxRtWb,avgCm,maxCm,allCm,maxCmWb,avgWb,maxWb,allWb,maxDate);
    }
}
