package com.ilife.weiboservice.dao.daoimpl;

import com.ilife.weiboservice.dao.CommentDao;
import com.ilife.weiboservice.entity.Comment;
import com.ilife.weiboservice.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDaoImpl implements CommentDao {

    @Autowired
    private CommentsRepository commentsRepository;
    public List<Comment> findAllByUidAndDirection(Integer uid, String direction){
        return commentsRepository.findAllByUidAndDirection(uid,direction);
    }

    public Comment findByPid(Integer pid){
        return commentsRepository.findByPid(pid);
    }

    public void deleteByUid(Integer uid){
        commentsRepository.deleteByUid(uid);
    }

    public void deleteByPid(Integer pid){
        commentsRepository.deleteByPid(pid);
    }

    public void deleteByWid(Integer wid){
        commentsRepository.deleteByWid(wid);
    }

    public void updateComments(Integer pid,String text){
        commentsRepository.updateComments(pid,text);
    }

    public Comment save(Comment comment){
        commentsRepository.save(comment);
        return comment;
    }
}
