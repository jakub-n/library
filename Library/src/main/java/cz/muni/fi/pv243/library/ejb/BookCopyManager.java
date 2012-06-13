package cz.muni.fi.pv243.library.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.solder.logging.Logger;

import cz.muni.fi.pv243.library.entity.BookCopy;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;

@Stateless
public class BookCopyManager {
	

    @Inject
    @LibraryDatabase
	private EntityManager em;
    
    @Inject
    private Logger log;
	
	public void create(BookCopy copy) {
		em.persist(copy);
		log.infof("BookCopy created: %d",copy.getId());
	}
	
	public void delete(BookCopy copy) {
		//assert copy != null;
		em.remove(em.merge(copy));
		log.infof("BookCopy deleted: %d",copy.getId());
	}
	
	public void delete(Long bookCopyId){
			em.remove(em.find(BookCopy.class, bookCopyId));
			log.infof("BookCopy deleted: %d",bookCopyId);
	}
	
	public void update(BookCopy copy) {
		em.merge(copy);
		log.infof("BookCopy updated: %d",copy.getId());
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
