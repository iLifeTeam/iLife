package com.ilife.douban.repository;

import com.ilife.douban.entity.Recommendation;
import org.springframework.data.repository.CrudRepository;

public interface RcmdRepository extends CrudRepository<Recommendation, String> {

    Recommendation findAllById(String id);
}
