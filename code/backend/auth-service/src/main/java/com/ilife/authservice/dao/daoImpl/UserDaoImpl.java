package com.ilife.authservice.dao.daoImpl;


import com.ilife.authservice.dao.UserDao;
import com.ilife.authservice.entity.Users;
import com.ilife.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Users findById(Long id) {
        return userRepository.findAllById(id);
    }

    @Override
    public Users findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    @Override
    public Users findByAccount(String account){
        return userRepository.findByAccount(account);
    }

    @Override
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public void save(Users user) {
        userRepository.save(user);
    }

    @Override
    public int updateWyyId(Long id, Long wyyId){
        return userRepository.updateWyyId(id, wyyId);
    }

    @Override
    public int updateWbId(Long id, Long wbId){
        return userRepository.updateWbId(id, wbId);
    }

    @Override
    public int updateZhId(Long id, String zhId){
        return userRepository.updateZhId(id, zhId);
    }

}
