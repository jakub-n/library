package cz.muni.fi.pv243.library.web;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import cz.muni.fi.pv243.library.ejb.RequestBean;
import cz.muni.fi.pv243.library.entity.Book;
import cz.muni.fi.pv243.library.entity.BookCopy;


@ManagedBean
@SessionScoped
public class TestWebBean {

	@Inject
	private RequestBean requestBean;

	public void addDefaultBook() {
		Book b = new Book();
		b.setTitle("Introduction into Java EE 6");
		requestBean.addBook(b);
	}

	public List<Book> getBooks() {
		return requestBean.getAllBooks();
	}

	public void deleteBooks() {
		requestBean.deleteAllBooks();
	}
	
	public void addDefaultBookCopy(Book book) {
		BookCopy bc = new BookCopy();
		bc.setBook(book);
		requestBean.addBookCopy(bc);
	}
	
	public List<BookCopy> getBookCopies() {
		return requestBean.getAllBookCopies();
	}

}
