package com.ilife.douban.service;


import com.ilife.douban.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    User findById(String id);

    ResponseEntity<?> deleteById(String id);

}
