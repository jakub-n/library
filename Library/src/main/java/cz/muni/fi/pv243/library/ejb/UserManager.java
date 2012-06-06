package cz.muni.fi.pv243.library.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import cz.muni.fi.pv243.library.entity.User;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;

/**
 * Class to handle the users generally.
 */
@Stateless
public class UserManager {

    @Inject
    @LibraryDatabase
	private EntityManager em;


	/**
	 * Returns user of given username, null if doesn't exist.
	 * 
	 * @param username of user to be returned
	 * @return user of given username, null if doesn't exist
	 */
	public User getUserByUsername(String username) {
		Query createQuery = em
				.createQuery("SELECT u FROM User u WHERE u.username = :username");
		createQuery.setParameter("username", username);

		if (createQuery.getResultList().size() > 0) {
			return (User) createQuery.getSingleResult();
		} else {
			return null;
		}
	}

}
