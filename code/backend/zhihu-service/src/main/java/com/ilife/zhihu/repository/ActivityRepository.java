package com.ilife.zhihu.repository;

import com.ilife.zhihu.entity.Activity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, String> {

}
