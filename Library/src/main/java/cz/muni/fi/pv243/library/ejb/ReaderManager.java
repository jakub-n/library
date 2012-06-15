package cz.muni.fi.pv243.library.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jboss.solder.logging.Logger;

import cz.muni.fi.pv243.library.entity.Reader;
import cz.muni.fi.pv243.library.entity.User;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;

@Stateless
public class ReaderManager {

	@Inject
	@LibraryDatabase
	private EntityManager em;

	@Inject
	private Logger log;

	/**
	 * Persists new reader
	 * 
	 * @param reader
	 */
	public void create(Reader reader) {
		em.persist(reader);
		log.infof("Reader created: %s", reader.getUsername());
	}

	/**
	 * Removes given reader
	 * 
	 * @param reader
	 */
	@RolesAllowed({ "MANAGER", "LIBRARIAN" })
	public void delete(Reader reader) {
		em.remove(em.merge(reader));
		log.infof("Reader deleted: %s", reader.getUsername());
	}

	/**
	 * Updates given reader
	 * 
	 * @param reader
	 */
	@RolesAllowed({ "MANAGER", "LIBRARIAN" })
	public void update(Reader reader) {
		em.merge(reader);
		log.infof("Reader updated: %s", reader.getUsername());
	}

	/**
	 * Returns all readers
	 * 
	 * @return all readers
	 */
	public List<Reader> getAllReaders() {
		TypedQuery<Reader> q = em.createQuery("SELECT a FROM Reader a",
				Reader.class);
		return q.getResultList();
	}

	/**
	 * Returns reader with given username
	 * 
	 * @param username
	 * @return reader with given username
	 */
	public Reader getReaderByName(String username) {
		Query q = em.createQuery(
				"SELECT a FROM Reader a WHERE a.username=:name").setParameter(
				"name", username);
		if (q.getResultList().size() > 0) {
			return (Reader) q.getSingleResult();
		} else {
			return null;
		}
	}

	/**
	 * Returns count of readers.
	 * 
	 * @return count of readers
	 */
	@RolesAllowed({ "MANAGER", "LIBRARIAN" })
	public int count() {
		Query query = em.createQuery("SELECT count(a) FROM Reader a");
		return ((Long) query.getSingleResult()).intValue();
	}

	/**
	 * Returns readers by given range.
	 * 
	 * @param range
	 * @return readers by given range
	 */
	@RolesAllowed({ "MANAGER", "LIBRARIAN" })
	public List<Reader> findRange(int[] range, String username) {
		Query query = null;
		if (username == null) {
			query = em.createQuery("SELECT a FROM Reader a");
		} else {
			query = em
					.createQuery("SELECT a FROM Reader a WHERE LOWER(a.username) LIKE '%"
							+ username.toLowerCase() + "%' ");
		}
		query.setFirstResult(range[0]);
		query.setMaxResults(range[1] - range[0]);
		return query.getResultList();
	}

}
