package com.ilife.zhihu.repository;

import com.ilife.zhihu.entity.Activity;
import com.ilife.zhihu.entity.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Integer> {
    
}
