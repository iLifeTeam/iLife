package com.ilife.musicservice.dao.daoimpl;

import com.ilife.musicservice.dao.MusicsDao;
import com.ilife.musicservice.dao.WyyhistoryDao;
import com.ilife.musicservice.entity.musics;
import com.ilife.musicservice.repository.MusicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MusicsDaoImpl implements MusicsDao {
    @Autowired
    private MusicsRepository musicsRepository;

    public void addmusic(Long id, String name){
        musicsRepository.addmusic(id,name);
    }




    public musics findById(Long id) {
        return musicsRepository.findById(id).orElse(null);
    }

    public Long getFavoriteSong(Long id){
        return musicsRepository.getFavoriteSong(id);
    }
}
