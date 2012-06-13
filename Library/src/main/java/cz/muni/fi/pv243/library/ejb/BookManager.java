package cz.muni.fi.pv243.library.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.solder.logging.Logger;

import cz.muni.fi.pv243.library.entity.Book;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;


/**
 * A stateless bean handling the library entities.
 * 
 * @author Ixi (Iva Zakova)
 */
@Stateless
public class BookManager {

	

    @Inject
    @LibraryDatabase
	private EntityManager em;
    
    @Inject
    private Logger log;

	public void create(Book book) {
		em.persist(book);
		log.infof("Book created: %s", book.getBookId());
	}
	
    public void delete(Book book){
    	em.remove(em.merge(book));
    	log.infof("Book deleted: %s", book.getBookId());
    }
    
    public void update(Book book){
    	em.merge(book);
    	log.infof("Book updated: %s", book.getBookId());
    }

	public List<Book> getAllBooks() {
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
    	log.infof("All Books was deleted!");
    	
    }
	
	public List<Book> getBooksWithTitleContainingGivenText(String text) {
		Query query = em.createQuery("SELECT b FROM Book b WHERE LOWER(b.title) LIKE '%" + text.toLowerCase() + "%' ");
		return query.getResultList();
	}

	public List<Book> getBooksWithTitleStartingWithGivenText(String text) {
		Query query = em.createQuery("SELECT b FROM Book b WHERE LOWER(b.title) LIKE  '" + text.toLowerCase() + "%' ");
		return query.getResultList();
	}
	
	public List<Book> getBooksWithAuthorStartingWithGivenText(String text) {
		Query query = em.createQuery("SELECT b FROM Book b WHERE LOWER(b.author) LIKE  '" + text.toLowerCase() + "%' ");
		return query.getResultList();
	}
	
	public Book getBookById(Long id){
		Query query = em.createQuery("SELECT b FROM Book b WHERE b.bookId=" + id);
		return (Book) query.getSingleResult();
	}
	
	
}
