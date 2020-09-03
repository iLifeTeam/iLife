package com.ilife.douban.dao.daoImpl;

import com.ilife.douban.dao.RcmdDao;
import com.ilife.douban.dao.UserDao;
import com.ilife.douban.entity.Recommendation;
import com.ilife.douban.entity.User;
import com.ilife.douban.repository.RcmdRepository;
import com.ilife.douban.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RcmdDaoImpl implements RcmdDao {

    @Autowired
    private RcmdRepository rcmdRepository;

    @Override
    public Recommendation findById(String id){
        return rcmdRepository.findAllById(id);
    }

    @Override
    public void save(Recommendation rcmd){
        rcmdRepository.save(rcmd);
    }


}
