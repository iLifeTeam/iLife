package com.ilife.weiboservice.service.serviceimpl;

import com.ilife.weiboservice.dao.UserDao;
import com.ilife.weiboservice.dao.WeiboDao;
import com.ilife.weiboservice.entity.User;
import com.ilife.weiboservice.entity.Weibo;
import com.ilife.weiboservice.service.UserService;
import com.ilife.weiboservice.service.WeiboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findAllById(Long id) {
        return userDao.findAllById(id);
    }

    @Override
    public User findByNickname(String nickname) {
        return userDao.findByNickname(nickname);
    }

    @Override
    public ResponseEntity<?> deleteById(Long uid) {
        userDao.deleteById(uid);
        return ResponseEntity.ok().body("successful delete user " + uid.toString());
    }
}
