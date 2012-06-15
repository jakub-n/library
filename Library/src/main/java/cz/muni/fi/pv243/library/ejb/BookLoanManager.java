package cz.muni.fi.pv243.library.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jboss.solder.logging.Logger;

import cz.muni.fi.pv243.library.entity.BookLoan;
import cz.muni.fi.pv243.library.entity.Reader;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;

@Stateless
public class BookLoanManager {
	

    @Inject
    @LibraryDatabase
	private EntityManager em;
    
    @Inject
    private Logger log;
	
    /**
     * Persists new bookLoan
     * 
     * @param bookLoan
     */
	public void create(BookLoan bookLoan){
		em.persist(bookLoan);
		log.infof("BookLoan created: %s", bookLoan.getId());
	}
	
	/**
	 * Removes given bookLoan
	 * 
	 * @param bookLoan
	 */
	public void delete(BookLoan bookLoan){
		em.remove(em.merge(bookLoan));
		log.infof("BookLoan deleted: %s", bookLoan.getId());
	}
	
	/**
	 * Updates given bookLoan
	 * 
	 * @param bookLoan
	 */
	public void update(BookLoan bookLoan){
		em.merge(bookLoan);
		log.infof("BookLoan updated: %s", bookLoan.getId());
	}
	
	/**
	 * Returns all bookLoans
	 * @return all booLoans
	 */
	public List<BookLoan> getAllBookLoans(){
		TypedQuery<BookLoan> q  = em.createQuery("SELECT a FROM BookLoan a",BookLoan.class);
		return q.getResultList();
	}
	

	/**
	 * Returns count of book loans for reader.
	 * 
	 * @param reader
	 * @return count of book loans
	 */
	@RolesAllowed({ "MANAGER", "LIBRARIAN" })
	public int count(Reader reader) {
		Query query = em
				.createQuery("SELECT count(a) FROM BookLoan a WHERE a.reader.username='"
						+ reader.getUsername() + "'");
		return ((Long) query.getSingleResult()).intValue();
	}

	/**
	 * Returns active book loans by given range.
	 * 
	 * @param range
	 * @return old book loans by given range
	 */
	@RolesAllowed({ "MANAGER", "LIBRARIAN" })
	public List<BookLoan> findRange(int[] range, Reader reader) {
		Query query = em
				.createQuery("SELECT a FROM BookLoan a WHERE a.reader.username='"
						+ reader.getUsername() + "' AND endDate IS NULL");

		query.setFirstResult(range[0]);
		query.setMaxResults(range[1] - range[0]);
		return query.getResultList();
	}
	
	/**
	 * Returns old book loans by given range.
	 * 
	 * @param range
	 * @return old book loans by given range
	 */
	@RolesAllowed({ "MANAGER", "LIBRARIAN" })
	public List<BookLoan> findRangeOldLoans(int[] range, Reader reader) {
		Query query = em
				.createQuery("SELECT a FROM BookLoan a WHERE a.reader.username='"
						+ reader.getUsername() + "' AND endDate NOT NULL");

		query.setFirstResult(range[0]);
		query.setMaxResults(range[1] - range[0]);
		return query.getResultList();
	}
	

}
