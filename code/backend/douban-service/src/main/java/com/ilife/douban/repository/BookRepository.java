package com.ilife.douban.repository;

import com.ilife.douban.entity.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, String> {

    List<Book> findAllById(String id);


}
