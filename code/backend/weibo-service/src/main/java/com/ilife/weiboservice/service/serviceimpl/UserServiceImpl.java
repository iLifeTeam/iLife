package com.ilife.weiboservice.service.serviceimpl;

import com.ilife.weiboservice.dao.UserDao;
import com.ilife.weiboservice.entity.User;
import com.ilife.weiboservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        if(userDao.findAllById(uid)!=null)
        {
            userDao.deleteById(uid);
            return ResponseEntity.ok().body("successful delete user " + uid.toString());
        }else{
            return ResponseEntity.status(501).body("user "+uid.toString()+" not exist");
        }
    }
}
