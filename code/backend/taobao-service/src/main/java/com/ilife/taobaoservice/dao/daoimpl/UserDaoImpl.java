package com.ilife.taobaoservice.dao.daoimpl;

import com.ilife.taobaoservice.dao.UserDao;
import com.ilife.taobaoservice.entity.User;
import com.ilife.taobaoservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findById(username).orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
