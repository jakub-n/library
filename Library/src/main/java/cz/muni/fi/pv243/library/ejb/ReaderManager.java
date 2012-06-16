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

import cz.muni.fi.pv243.library.entity.Reader;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;

@DeclareRoles({ "MANAGER", "LIBRARIAN" })
@SecurityDomain("library")
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
		this.em.persist(reader);
		this.log.infof("Reader created: %s", reader.getUsername());
	}

	/**
	 * Removes given reader
	 * 
	 * @param reader
	 */
	@RolesAllowed({ "MANAGER", "LIBRARIAN" })
	public void delete(Reader reader) {
		this.em.remove(this.em.merge(reader));
		this.log.infof("Reader deleted: %s", reader.getUsername());
	}

	/**
	 * Updates given reader
	 * 
	 * @param reader
	 */
	@RolesAllowed({ "MANAGER", "LIBRARIAN" })
	public void update(Reader reader) {
		this.em.merge(reader);
		this.log.infof("Reader updated: %s", reader.getUsername());
	}

	/**
	 * Returns all readers
	 * 
	 * @return all readers
	 */
	public List<Reader> getAllReaders() {
		TypedQuery<Reader> q = this.em.createQuery("SELECT a FROM Reader a",
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
		Query q = this.em.createQuery(
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
		Query query = this.em.createQuery("SELECT count(a) FROM Reader a");
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
		TypedQuery<Reader> query = null;
		if (username == null) {
			query = this.em.createQuery("SELECT a FROM Reader a",Reader.class);
		} else {
			query = this.em
					.createQuery("SELECT a FROM Reader a WHERE LOWER(a.username) LIKE '%"
							+ username.toLowerCase() + "%' ", Reader.class);
		}
		query.setFirstResult(range[0]);
		query.setMaxResults(range[1] - range[0]);
		return query.getResultList();
	}

}
