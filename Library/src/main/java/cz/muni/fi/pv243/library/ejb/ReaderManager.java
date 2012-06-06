package cz.muni.fi.pv243.library.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.PersistenceException;

import cz.muni.fi.pv243.library.entity.Reader;
import cz.muni.fi.pv243.library.entity.User;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;

@Stateless
public class ReaderManager {


    @Inject
    @LibraryDatabase
	private EntityManager em;

	public void create(Reader reader) {
		em.persist(reader);
	}

//	public void delete(Reader reader) {
//		if (em.merge(reader).getLoans().isEmpty()) {
//			em.remove(em.merge(reader));
//		} else {
//			System.out.println("Reader in relation with bookLoan!");
//		}
//	}
	public void delete(Reader reader) {
			em.remove(em.merge(reader));
	}

	public void update(Reader reader) {
		em.merge(reader);
	}

	public List<Reader> getAllReaders() {
		Query q = em.createQuery("SELECT a FROM Reader a");
		return q.getResultList();
	}
	

}
