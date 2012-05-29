package cz.muni.fi.pv243.library.web.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import cz.muni.fi.pv243.library.ejb.BookManager;
import cz.muni.fi.pv243.library.entity.Book;

@ViewScoped
@ManagedBean
public class BookDetailController {

	@Inject
	private BookManager bookManager;
	
	
	private Book book;
	
	public BookManager getBookManager() {
		return bookManager;
	}
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public void setBookManager(BookManager bookManager) {
		this.bookManager = bookManager;
	}
	
	public void printBook(){
		System.out.println(book);
		
	}
	

}
