package com.adt.sample.devops.service;

import java.util.List;

import com.adt.sample.devops.model.Book;

public interface BookService {

	public long save(Book book);

	public Book get(long id);

	public List<Book> list();

	public void update(long id, Book book);

	public void delete(long id);
}
