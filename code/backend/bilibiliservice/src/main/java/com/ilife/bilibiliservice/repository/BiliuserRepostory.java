package com.ilife.bilibiliservice.repository;

import com.ilife.bilibiliservice.entity.biliuser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiliuserRepostory extends JpaRepository<biliuser,Long> {
}
