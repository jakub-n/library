package cz.muni.fi.pv243.library.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pv243.library.entity.Book;
import cz.muni.fi.pv243.library.entity.User;

/**
 * Class to handle the users generally.
 */
@Stateless
public class UserManager {

	@PersistenceContext
	private EntityManager em;

	public void create(User user) {
		em.persist(user);
	}

	public void delete(User user) {
		em.remove(em.merge(user));
	}

	public void update(Book user) {
		em.merge(user);
	}

	public List<User> getAllUsers() {
		Query query = em.createQuery("SELECT u FROM User u");
		return query.getResultList();
	}

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
