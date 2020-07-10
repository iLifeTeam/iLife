//package com.ilife.weiboservice.repository;
//
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.repository.CrudRepository;
//import com.ilife.weiboservice.entity.Comment;
//import org.springframework.data.jpa.repository.Query;
//import javax.transaction.Transactional;
//import java.util.List;
//
//public interface CommentsRepository extends CrudRepository<Comment,Integer>{
//
//    //get a list of comments by uid and direction(received/published )
//    List<Comment> findAllByUidAndDirection(Integer uid, String direction);
//
//    Comment findByPid(Integer pid);
//
//    @Transactional
//    @Modifying
//    void deleteByUid(Integer uid);
//
//    @Transactional
//    @Modifying
//    void deleteByPid(Integer pid);
//
//    @Transactional
//    @Modifying
//    void deleteByWid(Integer wid);
//
//    @Transactional
//    @Modifying
//    @Query(value="update Comment set text=?2 where pid=?1 ")
//    void updateComments(Integer pid,String text);
//}
//
