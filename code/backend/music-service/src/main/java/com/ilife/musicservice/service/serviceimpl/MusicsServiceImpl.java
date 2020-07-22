package com.ilife.musicservice.service.serviceimpl;

import com.ilife.musicservice.dao.MusicsDao;
import com.ilife.musicservice.entity.musics;
import com.ilife.musicservice.repository.MusicsRepository;
import com.ilife.musicservice.service.MusicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicsServiceImpl implements MusicsService {

    @Autowired
    private MusicsDao musicsDao;


    public  void addmusic(Long id,String name){
        musicsDao.addmusic(id, name);
    }



}
