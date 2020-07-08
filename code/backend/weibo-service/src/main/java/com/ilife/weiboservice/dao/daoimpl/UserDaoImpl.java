package com.ilife.weiboservice.dao.daoimpl;

import com.ilife.weiboservice.dao.UserDao;
import com.ilife.weiboservice.entity.User;
import com.ilife.weiboservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userRepository;

    public User findByUid(Integer uid){
        return userRepository.findByUid(uid);
    }

    public User findByNickname(String nickname){
        return userRepository.findByNickname(nickname);
    }

    public void deleteByUid(Integer uid){
        userRepository.deleteByUid(uid);
    }

    public User save(User user){
        userRepository.save(user);
        return user;
    }

}
