package cz.muni.fi.pv243.library.ejb;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import cz.muni.fi.pv243.library.entity.Book;
import cz.muni.fi.pv243.library.entity.Librarian;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;
import org.jboss.ejb3.annotation.SecurityDomain;

@DeclareRoles({"MANAGER","LIBRARIAN"})
@SecurityDomain("library")
@Stateless
public class LibrarianManager {
	

    @Inject
    @LibraryDatabase
	private EntityManager em;
	
    /**
     * Saves new librarian.
     * 
     * @param librarian librarian to be saved
     */
    @RolesAllowed({"MANAGER"})
	public void create(Librarian librarian){
		em.persist(librarian);
	}

    /**
     * Deactivates librarian.
     * 
     * @param librarian
     */
    @RolesAllowed({"MANAGER"})
	public void delete(Librarian librarian){
		librarian.setActive(false);
		em.merge(librarian);
	}
	
    /**
     * Updates given librarian.
     * 
     * @param librarian librarian to be updated
     */
    @RolesAllowed({"MANAGER","LIBRARIAN"})
	public void update(Librarian librarian){
		em.merge(librarian);
	}
	
    /**
     * Returns count of active librarians.
     * 
     * @return count of active librarians
     */
    @RolesAllowed({"MANAGER"})
	public int count() {
		Query query = em.createQuery("SELECT count(l) FROM Librarian l WHERE l.active=true ");
		return ((Long) query.getSingleResult()).intValue();
	    }
    
    /**
     * Returns active librarians by given range.
     * 
     * @param range
     * @return active librarians by given range
     */
    @RolesAllowed({"MANAGER"})
	public List<Librarian> findRange(int[] range) {
		Query query = em.createQuery("SELECT l FROM Librarian l WHERE l.active=true");
		query.setFirstResult(range[0]);
		query.setMaxResults(range[1] - range[0]);
		return query.getResultList();
    }
	
    /**
     * Returns all active librarians. 
     * 
     * @return all active librarians
     */
    @RolesAllowed({"MANAGER"})
	public List<Book> getAllLibrarians() {
		Query query = em.createQuery("SELECT l FROM Librarian l WHERE l.active=true");
		return query.getResultList();
	}
	
	/**
	 * Returns librarian of given username, null if doesn't exist.
	 * 
	 * @param username of librarian to be returned
	 * @return librarian of given username, null if doesn't exist
	 */
    @RolesAllowed({"MANAGER","LIBRARIAN"})
	public Librarian getLibrarianByUsername(String username) {
		Query createQuery = em
				.createQuery("SELECT l FROM Librarian l WHERE l.username = :username");
		createQuery.setParameter("username", username);

		if (createQuery.getResultList().size() > 0) {
			return (Librarian) createQuery.getSingleResult();
		} else {
			return null;
		}
	}
}
