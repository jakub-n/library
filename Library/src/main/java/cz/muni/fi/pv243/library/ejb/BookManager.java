package cz.muni.fi.pv243.library.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pv243.library.entity.Book;
import cz.muni.fi.pv243.library.entity.BookCopy;;


/**
 * A stateless bean handling the library entities.
 * 
 * @author Ixi (Iva Zakova)
 */
@Stateless
public class BookManager {

	
	@PersistenceContext
	private EntityManager em;

	public void create(Book book) {
		em.persist(book);
	}
	
    public void delete(Book book){
    	em.remove(em.merge(book));
    }
    
    public void update(Book book){
    	em.merge(book);
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
		Query query = em.createQuery("SELECT b FROM Book b");
		return query.getResultList();
	}
	
	@Deprecated
    public void deleteAllBooks()
    {
    	Query query  = em.createQuery("DELETE FROM BookCopy");
    	Query query1 = em.createQuery("DELETE FROM Book ");
    	query.executeUpdate();
    	query1.executeUpdate();
    	
    	
    }

}