package com.ilife.douban.service.serviceImpl;

import com.ilife.douban.dao.MovieDao;
import com.ilife.douban.entity.BookStats;
import com.ilife.douban.entity.Movie;
import com.ilife.douban.service.UserService;
import com.ilife.douban.dao.BookDao;
import com.ilife.douban.dao.UserDao;
import com.ilife.douban.entity.Book;
import com.ilife.douban.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private MovieDao movieDao;

    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }

    @Override
    public ResponseEntity<?> deleteById(String id) {
        if(userDao.findById(id)==null)
            return ResponseEntity.status(501).body("User not exists");
        userDao.deleteById(id);
        return ResponseEntity.ok().body("successfully delete user " + id);
    }

    @Override
    public List<Book> getBooksById(String uid){
        return bookDao.findById(uid);
    }

    @Override
    public List<Movie> getMoviesById(String uid){
        return movieDao.findById(uid);
    }

    @Override
    public ResponseEntity<?> deleteBooks(String uid){
        User user = userDao.findById(uid);
        if(user == null)
            return ResponseEntity.status(501).body("user not exists");
        bookDao.DeleteAllById(uid);
        return ResponseEntity.ok().body("successfully delete");
    }

    @Override
    public ResponseEntity<?> deleteMovies(String uid){
        User user = userDao.findById(uid);
        if(user == null)
            return ResponseEntity.status(501).body("user not exists");
        movieDao.DeleteAllById(uid);
        return ResponseEntity.ok().body("successfully delete");
    }

    @Override
    public BookStats getBookStats(String uid){
        List<Book> bookList = bookDao.findById(uid);
        float avgPrice=0,maxPrice=0,avgRanking=0,maxRanking=0,avgHot=0,maxHot=0,minHot=0;
        Book maxPriceBook,maxRankingBook,maxHotBook;
        Integer allBook=0,allPrice=0;
        String preAuthor;
        for(Book book:bookList){
            allPrice+=book.getPrice().replace('元');

        }
    }





}
