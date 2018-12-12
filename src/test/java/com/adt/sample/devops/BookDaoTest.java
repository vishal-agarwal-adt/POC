package com.adt.sample.devops;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.adt.sample.devops.config.AppConfigTest;
import com.adt.sample.devops.dao.BookDao;
import com.adt.sample.devops.model.Book;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
@ActiveProfiles("test")
@Transactional
public class BookDaoTest {
    @Autowired
    BookDao bookDao;
    
    
    @Autowired
    private SessionFactory sessionFactory;
    
    
    @Test
    public void save_test( ) {
	Book book = getDefaultBookObject();
	bookDao.save(book);
	Book savedObj = sessionFactory.getCurrentSession().get(Book.class, book.getId());
	Assert.assertTrue(book.equals(savedObj));
    }
    
    @Test
    public void get_test() {
	Book book = getDefaultBookObject();
	sessionFactory.getCurrentSession().save(book);
	Book savedObj = bookDao.get(book.getId());
	Assert.assertTrue(book.equals(savedObj));
    }
    
    @Test
    public void list_test() {
	List<Book> books = new ArrayList<Book>();
	for(int i = 0; i < 2; i++) {
	    Book book = getDefaultBookObject();
	    books.add(book);
	    sessionFactory.getCurrentSession().save(book);
	}
	List<Book> allBooks = bookDao.list();
	Assert.assertTrue(allBooks.containsAll(books));
    }
    
    @Test
    public void update_test() {
	Book book = getDefaultBookObject();
	sessionFactory.getCurrentSession().save(book);
	Book savedObj = sessionFactory.getCurrentSession().get(Book.class, book.getId());
	String updatedAuthor = "updated_" + Calendar.getInstance().getTimeInMillis();
	savedObj.setAuthor(updatedAuthor);
	bookDao.update(savedObj.getId(), savedObj);
	savedObj = sessionFactory.getCurrentSession().get(Book.class, book.getId());
	Assert.assertTrue(updatedAuthor.equals(savedObj.getAuthor()));
    }
    
    @Test
    public void delete_test() {
	Book book = getDefaultBookObject();
	sessionFactory.getCurrentSession().save(book);
	Long bookId = book.getId();
	bookDao.delete(bookId);
	Book savedObj = sessionFactory.getCurrentSession().get(Book.class, bookId);
	Assert.assertNull(savedObj);
    }
    
    public Book getDefaultBookObject() {
	Book book = new Book();
	String time = String.valueOf(Calendar.getInstance().getTimeInMillis());
	book.setAuthor("ATH_" + time);
	book.setTitle("TTL_"+time);
	return book;
    }
}
