package com.ilife.weiboservice.repository;

import org.springframework.data.repository.CrudRepository;
import com.ilife.weiboservice.entity.Comment;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import java.util.List;

public interface commentsRepository {

    @Query("from Comment where u_id=?1 and direction=?2 ")
    List<Comment> findCommentsByUid(Integer uid, String type);

    @Query("from Comment where p_id=?1")
    Comment findCommentByPid(Integer pid, String type);

}
