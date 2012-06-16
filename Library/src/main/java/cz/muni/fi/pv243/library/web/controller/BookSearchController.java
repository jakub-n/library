package cz.muni.fi.pv243.library.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import cz.muni.fi.pv243.library.ejb.BookManager;
import cz.muni.fi.pv243.library.entity.Book;

@ManagedBean
@RequestScoped
public class BookSearchController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private BookManager bookManager;

	private String text;

	/**
	 * Returns all books.
	 * 
	 * @return all stored books
	 */
	public List<Book> getBooks() {
		return this.bookManager.getAllBooks();
	}

	/**
	 * Returns books of which title contains text.
	 * 
	 * @return books with title containing text
	 */
	public List<Book> getBooksContainingSearched() {
		return this.bookManager.getBooksWithTitleContainingGivenText(this.text);
	}

	/**
	 * Returns books starting with text.
	 * 
	 * @return books starting with text
	 */
	public List<Book> getBooksStartingWith() {
		return this.bookManager
				.getBooksWithTitleStartingWithGivenText(this.text);
	}

	/**
	 * Returns books where authors starts with text.
	 * 
	 * @return books where authors starts with text
	 */
	public List<Book> getBooksWithAuthorStartingWith() {
		return this.bookManager
				.getBooksWithAuthorStartingWithGivenText(this.text);
	}

	/**
	 * Returns bookManager.
	 * 
	 * @return bookManager
	 */
	public BookManager getBookManager() {
		return this.bookManager;
	}

	/**
	 * Sets bookManager.
	 * 
	 * @param bookManager
	 */
	public void setBookManager(BookManager bookManager) {
		this.bookManager = bookManager;
	}

	/**
	 * Set text
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Set text from page
	 * 
	 * @param text
	 */
	public void TextToBrowseSet(String text) {
		this.text = text;
	}

	/**
	 * Returns text.
	 * 
	 * @return text
	 */
	public String getText() {
		return this.text;
	}

}
