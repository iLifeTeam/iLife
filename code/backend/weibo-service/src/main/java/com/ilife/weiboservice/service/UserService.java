package com.ilife.weiboservice.service;

import com.ilife.weiboservice.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    User findAllById(Long id);

    User findByNickname(String nickname);

    ResponseEntity<?> deleteById(Long uid);
}
