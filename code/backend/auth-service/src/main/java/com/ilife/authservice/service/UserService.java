package com.ilife.authservice.service;


import com.ilife.authservice.entity.Users;
import org.springframework.http.ResponseEntity;

public interface UserService {

    Users findById(Long id);

    Users findByNickname(String nickname);

    ResponseEntity<?> deleteById(Long id);

    ResponseEntity<?> save(String nickname, String account, String password, String email,String type);

    ResponseEntity<?> updateWyyId(Long id, Long wyyId);

    ResponseEntity<?> updateWbId(Long id, Long wbId);

    ResponseEntity<?> updateZhId(Long id, String zhId);

    Users findByAccount(String account);

    ResponseEntity<?> auth(String account, String password);
}
