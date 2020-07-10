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

    public User findAllById(Integer id) {
        return userRepository.findAllById(id);
    }

    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    public User save(User user) {
        userRepository.save(user);
        return user;
    }

}
