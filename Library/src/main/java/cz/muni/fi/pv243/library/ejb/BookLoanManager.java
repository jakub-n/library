package cz.muni.fi.pv243.library.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pv243.library.entity.BookLoan;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;

@Stateless
public class BookLoanManager {
	

    @Inject
    @LibraryDatabase
	private EntityManager em;
	
	public void create(BookLoan bookLoan){
		em.persist(bookLoan);
	}
	
	public void delete(BookLoan bookLoan){
		em.remove(em.merge(bookLoan));
	}
	
	public void update(BookLoan bookLoan){
		em.merge(bookLoan);
	}
	
	public List<BookLoan> getAllBookLoans(){
		Query q  = em.createQuery("SELECT a FROM BookLoan a");
		return q.getResultList();
	}
	
	

}
