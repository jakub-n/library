package cz.muni.fi.pv243.library.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pv243.library.entity.BookCopy;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;

@Stateless
public class BookCopyManager {
	

    @Inject
    @LibraryDatabase
	private EntityManager em;
	
	public void create(BookCopy copy) {
		em.persist(copy);
	}
	
	public void delete(BookCopy copy) {
		//assert copy != null;
		em.remove(em.merge(copy));
	}
	
//	public void delete(Long bookCopyId){
//		if (em.merge(em.find(BookCopy.class, bookCopyId)).getLoans().isEmpty()) {
//			em.remove(em.find(BookCopy.class, bookCopyId));
//		} else {
//			System.out.println("bookCopy in relation with bookLoan!");
//		}
//	}
	
	public void delete(Long bookCopyId){
			em.remove(em.find(BookCopy.class, bookCopyId));
	}
	
	public void update(BookCopy copy) {
		em.merge(copy);
	}
	
	public List<BookCopy> getAllBookCopies() {
		Query query = em.createQuery("SELECT c FROM BookCopy c");
		return query.getResultList();
	}
	
	@Deprecated
	public void deleteAllBookCopies() {
		Query q = em.createQuery("DELETE FROM BookCopy");
		q.executeUpdate();
	}
	

}
