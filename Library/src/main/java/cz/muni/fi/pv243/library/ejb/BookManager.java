package cz.muni.fi.pv243.library.ejb;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.solder.logging.Logger;

import cz.muni.fi.pv243.library.entity.Book;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;


/**
 * A stateless bean handling the library entities.
 * 
 * @author Ixi (Iva Zakova)
 */

@DeclareRoles({"MANAGER","LIBRARIAN"})
@SecurityDomain("library")
@Stateless
public class BookManager {

	

    @Inject
    @LibraryDatabase
	private EntityManager em;
    
    @Inject
    private Logger log;

    /**
     * Persists new book
     * 
     * @param book
     */
    @RolesAllowed({ "MANAGER", "LIBRARIAN" })
	public void create(Book book) {
		em.persist(book);
		log.infof("Book created: %s", book.getBookId());
	}
	
	/**
	 * Removes given book
	 * 
	 * @param book
	 */
	@RolesAllowed({ "MANAGER", "LIBRARIAN" })
    public void delete(Book book){
    	em.remove(em.merge(book));
    	log.infof("Book deleted: %s", book.getBookId());
    }
    
    /**
     * Updates given book
     * 
     * @param book
     */
	@RolesAllowed({ "MANAGER", "LIBRARIAN" })
    public void update(Book book){
    	em.merge(book);
    	log.infof("Book updated: %s", book.getBookId());
    }

    /**
     * Returns all books
     * 
     * @return all books
     */
	public List<Book> getAllBooks() {
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b",Book.class);
		return query.getResultList();
	}
	
	/**
	 * Removes all books
	 * 
	 * @deprecated
	 */
	@Deprecated
    public void deleteAllBooks()
    {
    	Query query  = em.createQuery("DELETE FROM BookCopy");
    	Query query1 = em.createQuery("DELETE FROM Book ");
    	query.executeUpdate();
    	query1.executeUpdate();
    	log.infof("All Books was deleted!");
    	
    }
	
	/**
	 * Returns all books with title containing given text
	 * 
	 * @param text
	 * @return
	 */
	public List<Book> getBooksWithTitleContainingGivenText(String text) {
		TypedQuery<Book> query 
		= em.createQuery
		("SELECT b FROM Book b WHERE LOWER(b.title) LIKE '%" + text.toLowerCase() + "%' ",Book.class);
		return query.getResultList();
	}

	/**
	 * Returns all books with title starting with given text
	 * 
	 * @param text
	 * @return
	 */
	public List<Book> getBooksWithTitleStartingWithGivenText(String text) {
		TypedQuery<Book> query 
		= em.createQuery
		("SELECT b FROM Book b WHERE LOWER(b.title) LIKE  '" + text.toLowerCase() + "%' ",Book.class);
		return query.getResultList();
	}
	
	/**
	 * Returns all books with author's name starting with given text
	 * 
	 * @param text
	 * @return
	 */
	public List<Book> getBooksWithAuthorStartingWithGivenText(String text) {
		TypedQuery<Book> query 
		= em.createQuery
		("SELECT b FROM Book b WHERE LOWER(b.author) LIKE  '" + text.toLowerCase() + "%' ",Book.class);
		return query.getResultList();
	}
	
	/**
	 * Returns book with given id
	 * 
	 * @param id
	 * @return
	 */
	public Book getBookById(Long id){
		Query query = em.createQuery("SELECT b FROM Book b WHERE b.bookId=" + id);
		return (Book) query.getSingleResult();
	}
	
	
}
