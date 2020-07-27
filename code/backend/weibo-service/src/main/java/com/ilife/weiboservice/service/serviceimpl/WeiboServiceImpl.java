package com.ilife.weiboservice.service.serviceimpl;

import com.ilife.weiboservice.dao.UserDao;
import com.ilife.weiboservice.dao.WeiboDao;
import com.ilife.weiboservice.entity.Statistics;
import com.ilife.weiboservice.entity.Weibo;
import com.ilife.weiboservice.service.WeiboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class WeiboServiceImpl implements WeiboService {

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
    public Statistics getStats(Long uid){
        
    }
}
