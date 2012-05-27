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
	

	public BookManager getBookManager() {
		return bookManager;
	}

	public void setBookManager(BookManager bookManager) {
		this.bookManager = bookManager;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public void TextToBrowseSet(String text) {
		this.text = text;
	}

	public List<Book> getBooks() {
		return bookManager.getAllBooks();
	}
	
	public List<Book> getBooksContainingSearched()
	{
		return bookManager.getBooksWithTitleContainingGivenText(text);	
	}
	
	public List<Book> getBooksStartingWith()
	{
		return bookManager.getBooksWithTitleStartingWithGivenText(text);	
	}

	public List<Book> getBooksWithAuthorStartingWith()
	{
		return bookManager.getBooksWithAuthorStartingWithGivenText(text);	
	}
	


}
