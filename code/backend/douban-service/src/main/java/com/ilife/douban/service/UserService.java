package com.ilife.douban.service;


import com.ilife.douban.entity.Book;
import com.ilife.douban.entity.Movie;
import com.ilife.douban.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    User findById(String id);

    ResponseEntity<?> deleteById(String id);

    List<Book> getBooksById(String uid);

    List<Movie> getMoviesById(String uid);

    ResponseEntity<?> deleteBooks(String uid);

    ResponseEntity<?> deleteMovies(String uid);
}
