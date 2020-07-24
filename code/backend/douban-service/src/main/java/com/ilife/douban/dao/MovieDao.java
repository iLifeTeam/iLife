package com.ilife.douban.dao;

import com.ilife.douban.entity.Movie;

import java.util.List;

public interface MovieDao {
    List<Movie> findById(String id);

    void DeleteAllById(String id);
}
