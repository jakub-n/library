package cz.muni.fi.pv243.library.web.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import cz.muni.fi.pv243.library.ejb.BookManager;
import cz.muni.fi.pv243.library.entity.Book;


@ManagedBean
@RequestScoped
public class BookSearchController {

	@Inject
	private BookManager bookManager;
	
	private String text;
	

	/**
	 * Returns all books.
	 * 
	 * @return all stored books
	 */
	public List<Book> getBooks() {
		return bookManager.getAllBooks();
	}
	
	/**
	 *  Returns books of which title contain text.
	 *  
	 * @return books which title contatin text
	 */
	public List<Book> getBooksContainingSearched()
	{
		return bookManager.getBooksWithTitleContainingGivenText(text);	
	}
	
	/**
	 * Returns books starting with text.
	 * 
	 * @return books starting with text
	 */
	public List<Book> getBooksStartingWith()
	{
		return bookManager.getBooksWithTitleStartingWithGivenText(text);	
	}


	/**
	 * Returns books where authors starts with text.
	 * 
	 * @return books where authors starts with text
	 */
	public List<Book> getBooksWithAuthorStartingWith()
	{
		return bookManager.getBooksWithAuthorStartingWithGivenText(text);	
	}
	
	/**
	 * Returns bookManager.
	 * 
	 * @return bookManager
	 */
	public BookManager getBookManager() {
		return bookManager;
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
		return text;
	}

	
}
