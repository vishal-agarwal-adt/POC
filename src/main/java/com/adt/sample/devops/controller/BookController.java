package com.adt.sample.devops.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adt.sample.devops.model.Book;
import com.adt.sample.devops.service.BookService;

/**
 * The Class BookController {@link Book}.
 * 
 * @author vishal.agarwal
 */
@RestController
public class BookController {

	/** The book service. */
	@Autowired
	private BookService bookService;

	/**
	 * Save.
	 *
	 * @param book the book
	 * @return the response entity
	 */
	/*---Add new book---*/
	@PostMapping("/book")
	public ResponseEntity<?> save(@RequestBody Book book) {
		long id = bookService.save(book);
		return ResponseEntity.ok().body("New Book has been saved with ID:" + id);
	}

	/**
	 * Gets the.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	/*---Get a book by id---*/
	@GetMapping("/book/{id}")
	public ResponseEntity<Book> get(@PathVariable("id") long id) {
		Book book = bookService.get(id);
		return ResponseEntity.ok().body(book);
	}

	/**
	 * List.
	 *
	 * @return the response entity
	 */
	/*---get all books---*/
	@GetMapping("/book")
	public ResponseEntity<List<Book>> list() {
		List<Book> books = bookService.list();
		return ResponseEntity.ok().body(books);
	}

	/**
	 * Update.
	 *
	 * @param id the id
	 * @param book the book
	 * @return the response entity
	 */
	/*---Update a book by id---*/
	@PutMapping("/book/{id}")
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Book book) {
		bookService.update(id, book);
		return ResponseEntity.ok().body("Book has been updated successfully.");
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	/*---Delete a book by id---*/
	@DeleteMapping("/book/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		bookService.delete(id);
		return ResponseEntity.ok().body("Book has been deleted successfully.");
	}
}