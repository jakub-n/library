package pv243.library.web;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import pv243.library.ejb.RequestBean;
import pv243.library.entity.Book;

@ManagedBean
@SessionScoped
public class TestWebBean {

	@EJB
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

}
