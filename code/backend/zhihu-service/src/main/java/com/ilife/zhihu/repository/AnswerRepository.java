package com.ilife.zhihu.repository;

import com.ilife.zhihu.entity.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, String> {

}
