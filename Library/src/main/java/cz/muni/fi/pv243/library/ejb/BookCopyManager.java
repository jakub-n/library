package cz.muni.fi.pv243.library.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jboss.solder.logging.Logger;

import cz.muni.fi.pv243.library.entity.Book;
import cz.muni.fi.pv243.library.entity.BookCopy;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;

@Stateless
public class BookCopyManager {
	

    @Inject
    @LibraryDatabase
	private EntityManager em;
    
    @Inject
    private Logger log;
	
    /**
     * Persists new bookCopy.
     * 
     * @param copy
     */
	public void create(BookCopy copy) {
		em.persist(copy);
		log.infof("BookCopy created: %d",copy.getId());
	}
	
	/**
	 * Removes given booCopy
	 * 
	 * @param copy
	 */
	public void delete(BookCopy copy) {
		//assert copy != null;
		em.remove(em.merge(copy));
		log.infof("BookCopy deleted: %d",copy.getId());
	}
	
	/**
	 * Removes bookCopy with given id
	 * 
	 * @param bookCopyId
	 */
	public void delete(Long bookCopyId){
			em.remove(em.find(BookCopy.class, bookCopyId));
			log.infof("BookCopy deleted: %d",bookCopyId);
	}
	
	/**
	 * Updates given bookCopy
	 * 
	 * @param copy
	 */
	public void update(BookCopy copy) {
		em.merge(copy);
		log.infof("BookCopy updated: %d",copy.getId());
	}
	
	/**
	 * Returns all bookCopies
	 * 
	 * @return all bookCopies
	 */
	public List<BookCopy> getAllBookCopies() {
		TypedQuery<BookCopy> query = em.createQuery("SELECT c FROM BookCopy c", BookCopy.class);
		return query.getResultList();
	}
	
	/**
	 * Returns book copy by id
	 * 
	 * @return book copy
	 */
	public BookCopy getBookCopyById(Long id) {
		TypedQuery<BookCopy> query = em.createQuery("SELECT c FROM BookCopy c WHERE id='" + id + "'", BookCopy.class);
		if (query.getResultList().size() > 0) {
			return (BookCopy) query.getSingleResult();
		} else {
			return null;
		}
	}
	
	/**
	 * Removes all bookCopies. This method is Deprecated.
	 */
	@Deprecated
	public void deleteAllBookCopies() {
		Query q = em.createQuery("DELETE FROM BookCopy");
		q.executeUpdate();
	}
	
	/**
	 * Returns count of book copies by book
	 * 
	 * @return count
	 */
	public int countOfBookCopiesByBook(Book book) {
		Query query = em.createQuery("SELECT count(c) FROM BookCopy c WHERE book.id='" + book.getBookId() + "'");
		return ((Long) query.getSingleResult()).intValue();
	}
	
	/**
	 * Returns old book loans by given range.
	 * 
	 * @param range
	 * @return book copies by given range
	 */
	public List<BookCopy> findRangeBookCopies(int[] range, Book book) {
		TypedQuery<BookCopy> query = em.createQuery("SELECT c FROM BookCopy c WHERE book.id='" + book.getBookId() + "'", BookCopy.class);
		query.setFirstResult(range[0]);
		query.setMaxResults(range[1] - range[0]);
		return query.getResultList();
	}
}
