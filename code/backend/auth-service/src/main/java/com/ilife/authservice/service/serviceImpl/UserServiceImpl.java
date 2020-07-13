package com.ilife.authservice.service.serviceImpl;

import com.ilife.authservice.dao.UserDao;
import com.ilife.authservice.entity.Users;
import com.ilife.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Users findById(Long id){
        return userDao.findById(id);
    }

    @Override
    public Users findByNickname(String nickname){
        return userDao.findByNickname(nickname);
    }

    @Override
    public ResponseEntity<?> deleteById(Long id){
        userDao.deleteById(id);
        return ResponseEntity.ok().body("successfully delete user "+ id);
    }

    @Override
    public ResponseEntity<?> save(String nickname,String account,String password,String email){
        Users user=new Users(nickname,account,password,email);
        userDao.save(user);
        return ResponseEntity.ok().body("successfully save user");
    }

    @Override
    public ResponseEntity<?> updateWyyId(Long id, Long wyyId){
        userDao.updateWyyId(id, wyyId);
        return ResponseEntity.ok().body("successfully update user "+id+"'s wyyid "+ wyyId);
    }

}
