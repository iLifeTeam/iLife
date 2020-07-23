package com.ilife.douban.dao.daoImpl;

import com.ilife.douban.dao.UserDao;
import com.ilife.douban.entity.User;
import com.ilife.douban.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(String id) {
        return userRepository.findAllById(id);
    }


    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }


}
