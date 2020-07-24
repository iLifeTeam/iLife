package com.ilife.douban.repository;

import com.ilife.douban.entity.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, String> {
    List<Movie> findAllById(String id);
}
