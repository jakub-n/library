package pv243.library.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pv243.library.entity.Book;

/**
 * A stateless bean handling the library entities.
 * 
 * @author Ixi (Iva Zakova)
 */
@Stateless
public class RequestBean {

	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Default constructor.
	 */
	public RequestBean() {
	}

	public void addBook(Book book) {
		entityManager.persist(book);
		System.out.println("Book was added.");
	}
	


	public List<Book> getAllBooks() {
		/*List<Book> b = new ArrayList<Book>();
		Book b1=new Book();
		b1.setBookId(1);
		b1.setTitle("First");
		b.add(b1);
		Book b2=new Book();
		b2.setBookId(2);
		b2.setTitle("Second");
		b.add(b2);
		return b;*/
		Query query = entityManager.createQuery("SELECT b FROM Book b");
		return query.getResultList();
	}
	
    public void deleteAllBooks()
    {
    	Query query = entityManager.createQuery("DELETE FROM Book");
		query.executeUpdate();
    }

}
