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
    public Users findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public Users findByNickname(String nickname) {
        return userDao.findByNickname(nickname);
    }

    @Override
    public Users findByAccount(String account){
        return userDao.findByAccount(account);
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        if(userDao.findById(id)==null)
            return ResponseEntity.status(501).body("User not exists");
        userDao.deleteById(id);
        return ResponseEntity.ok().body("successfully delete user " + id);
    }

    @Override
    public ResponseEntity<?> save(String nickname, String account, String password, String email,String type) {
        if(findByAccount(account)!=null)
            return(ResponseEntity.status(500).body("Account already exists"));
        if(findByNickname(nickname)!=null)
            return(ResponseEntity.status(501).body("Nickname already exists"));
        Users user = new Users(nickname, account, password, email,type);
        userDao.save(user);
        return ResponseEntity.ok().body("successfully save user");
    }

    @Override
    public ResponseEntity<?> updateWyyId(Long id, Long wyyId) {
        int num=userDao.updateWyyId(id, wyyId);
        return ResponseEntity.ok().body(num);
    }

    @Override
    public ResponseEntity<?> updateWbId(Long id, Long wbId) {
        int num=userDao.updateWbId(id, wbId);
        return ResponseEntity.ok().body(num);
    }

    @Override
    public ResponseEntity<?> updateZhId(Long id, String zhId) {
        int num= userDao.updateZhId(id, zhId);
        return ResponseEntity.ok().body(num);
    }

    @Override
    public ResponseEntity<?> updateDbId(Long id, String dbId) {
        int num= userDao.updateDbId(id, dbId);
        return ResponseEntity.ok().body(num);
    }

    @Override
    public ResponseEntity<?> updateBiliId(Long id, String biliId) {
        int num= userDao.updateBiliId(id, biliId);
        return ResponseEntity.ok().body(num);
    }

    @Override
    public ResponseEntity<?> updateTbId(Long id, String tbId) {
        int num= userDao.updateTbId(id, tbId);
        return ResponseEntity.ok().body(num);
    }



    @Override
    public  ResponseEntity<?> auth(String account, String password){
        Users user;
        if((user=findByAccount(account))==null)
            return(ResponseEntity.status(501).body("Account not exists"));
        else if(!user.getPassword().equals(password))
            return(ResponseEntity.status(502).body("Account and password not match"));
        return ResponseEntity.ok().body("successfully auth");
    }



}
