package com.ilife.douban.dao.daoImpl;

import com.ilife.douban.dao.BookDao;
import com.ilife.douban.dao.MovieDao;
import com.ilife.douban.entity.Movie;
import com.ilife.douban.repository.BookRepository;
import com.ilife.douban.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class MovieDaoImpl implements MovieDao {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> findById(String id){
        return movieRepository.findAllById(id);
    }


}
