package com.ilife.douban.dao.daoImpl;

import com.ilife.douban.dao.BookDao;
import com.ilife.douban.entity.Book;
import com.ilife.douban.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findById(String id){
        return bookRepository.findAllById(id);
    }

    @Override
    public void DeleteAllById(String id){
        bookRepository.deleteAllById(id);
    }

}
