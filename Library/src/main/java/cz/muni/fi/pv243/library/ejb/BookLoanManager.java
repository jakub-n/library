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

import cz.muni.fi.pv243.library.entity.BookCopy;
import cz.muni.fi.pv243.library.entity.BookLoan;
import cz.muni.fi.pv243.library.entity.Reader;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;

@DeclareRoles({ "MANAGER", "LIBRARIAN" })
@SecurityDomain("library")
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
	public void create(BookLoan bookLoan) {
		this.em.persist(bookLoan);
		this.log.infof("BookLoan created: %s", bookLoan.getId());
	}

	/**
	 * Removes given bookLoan
	 * 
	 * @param bookLoan
	 */
	public void delete(BookLoan bookLoan) {
		this.em.remove(this.em.merge(bookLoan));
		this.log.infof("BookLoan deleted: %s", bookLoan.getId());
	}

	/**
	 * Updates given bookLoan
	 * 
	 * @param bookLoan
	 */
	public void update(BookLoan bookLoan) {
		this.em.merge(bookLoan);
		this.log.infof("BookLoan updated: %s", bookLoan.getId());
	}

	/**
	 * Returns all bookLoans
	 * 
	 * @return all booLoans
	 */
	public List<BookLoan> getAllBookLoans() {
		TypedQuery<BookLoan> q = this.em.createQuery(
				"SELECT a FROM BookLoan a", BookLoan.class);
		return q.getResultList();
	}

	/**
	 * Returns count of active book loans for reader.
	 * 
	 * @param reader
	 * @return count of active book loans
	 */
	@RolesAllowed({ "MANAGER", "LIBRARIAN", "READER" })
	public int count(Reader reader) {
		Query query = this.em
				.createQuery("SELECT count(a) FROM BookLoan a WHERE a.reader.username='"
						+ reader.getUsername() + "' AND endDate IS NULL");
		return ((Long) query.getSingleResult()).intValue();
	}

	/**
	 * Returns count of old book loans for reader.
	 * 
	 * @param reader
	 * @return count of old book loans
	 */
	@RolesAllowed({ "MANAGER", "LIBRARIAN" })
	public int countOldLoans(Reader reader) {
		Query query = this.em
				.createQuery("SELECT count(a) FROM BookLoan a WHERE a.reader.username='"
						+ reader.getUsername() + "' AND endDate NOT NULL");
		return ((Long) query.getSingleResult()).intValue();
	}

	/**
	 * Returns active book loans by given range.
	 * 
	 * @param range
	 * @return active book loans by given range
	 */
	@RolesAllowed({ "MANAGER", "LIBRARIAN", "READER" })
	public List<BookLoan> findRange(int[] range, Reader reader) {
		TypedQuery<BookLoan> query = this.em
				.createQuery("SELECT a FROM BookLoan a WHERE a.reader.username='"
						+ reader.getUsername() + "' AND endDate IS NULL", BookLoan.class);

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
	@RolesAllowed({ "MANAGER", "LIBRARIAN", "READER" })
	public List<BookLoan> findRangeOldLoans(int[] range, Reader reader) {
		TypedQuery<BookLoan> query = this.em
				.createQuery("SELECT a FROM BookLoan a WHERE a.reader.username='"
						+ reader.getUsername() + "' AND endDate NOT NULL",BookLoan.class);

		query.setFirstResult(range[0]);
		query.setMaxResults(range[1] - range[0]);
		return query.getResultList();
	}

	/**
	 * Returns active book loan for given book copy.
	 * 
	 * @param bookCopy
	 * @return active book loan
	 */
	public BookLoan getActiveBookLoanForBookCopy(BookCopy bookCopy) {
		Query q = this.em
				.createQuery("SELECT a FROM BookLoan a WHERE endDate IS NULL AND bookCopy.id="
						+ bookCopy.getId());
		if (q.getResultList().size() > 0) {
			return (BookLoan) q.getSingleResult();
		} else {
			return null;
		}
	}

	// /**
	// * Returns book loan for given book copy.
	// *
	// * @param bookCopy
	// * @return active book loans
	// */
	// public List<BookLoan> getBookLoansForBookCopy(BookCopy bookCopy) {
	// Query q = em.createQuery("SELECT a FROM BookLoan a WHERE bookCopy.id="
	// + bookCopy.getId());
	// return q.getResultList();
	// }

}
