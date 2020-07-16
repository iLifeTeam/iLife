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
    public void updateWyyId(Long id, Long wyyId){
        userRepository.updateWyyId(id, wyyId);
    }

    @Override
    public void updateWbId(Long id, Long wbId){
        System.out.println(userRepository.updateWbId(id, wbId));
    }

    @Override
    public void updateZhId(Long id, String zhId){
        userRepository.updateZhId(id, zhId);
    }

}
