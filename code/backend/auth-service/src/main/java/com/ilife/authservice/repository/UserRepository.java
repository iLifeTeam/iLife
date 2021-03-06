package com.ilife.authservice.repository;

import com.ilife.authservice.entity.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface UserRepository extends CrudRepository<Users, Long> {


    Users findAllById(Long id);

    Users findByNickname(String nickname);

    Users findByAccount(String account);

    @Transactional
    @Modifying
    void deleteById(Long uid);

    @Transactional
    @Modifying
    @Query(value = "update Users set wyyid=?2 where id =?1")
    int updateWyyId(Long id, Long wyyId);

    @Transactional
    @Modifying
    @Query(value = "update Users set weibid=?2 where id =?1")
    int updateWbId(Long id, Long wbId);

    @Transactional
    @Modifying
    @Query(value = "update Users set zhid=?2 where id =?1")
    int updateZhId(Long id, String zhId);


    @Transactional
    @Modifying
    @Query(value = "update Users set tbid=?2 where id =?1")
    int updateTbId(Long id, String tbId);

    @Transactional
    @Modifying
    @Query(value = "update Users set biliid=?2 where id =?1")
    int updateBiliId(Long id, Long biliId);

    @Transactional
    @Modifying
    @Query(value = "update Users set doubanid=?2 where id =?1")
    int updateDbId(Long id, String dbId);
}
