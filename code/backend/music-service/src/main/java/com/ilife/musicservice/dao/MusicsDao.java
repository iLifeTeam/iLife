package com.ilife.musicservice.dao;

import com.ilife.musicservice.entity.musics;

public interface MusicsDao {
    void addmusic(Long id, String name);
    musics findById(Long id);
}
