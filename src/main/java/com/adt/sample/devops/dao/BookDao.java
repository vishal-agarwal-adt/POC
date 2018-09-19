package com.adt.sample.devops.dao;

import java.util.List;

import com.adt.sample.devops.model.Book;

public interface BookDao {

	public long save(Book book);

	public Book get(long id);

	public List<Book> list();

	public void update(long id, Book book);

	public void delete(long id);

}
