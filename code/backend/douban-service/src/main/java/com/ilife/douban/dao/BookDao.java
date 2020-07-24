package com.ilife.douban.dao;

import com.ilife.douban.entity.Book;

import java.util.List;

public interface BookDao {
    List<Book> findById(String id);
}
