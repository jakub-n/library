package cz.muni.fi.pv243.library.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.PersistenceException;

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

	public void create(Reader reader) {
		em.persist(reader);
		log.infof("Reader created: %s", reader.getUsername());
	}

	public void delete(Reader reader) {
			em.remove(em.merge(reader));
			log.infof("Reader deleted: %s", reader.getUsername());
	}

	public void update(Reader reader) {
		em.merge(reader);
		log.infof("Reader updated: %s", reader.getUsername());
	}

	public List<Reader> getAllReaders() {
		Query q = em.createQuery("SELECT a FROM Reader a");
		return q.getResultList();
	}
	
	public Reader getReaderByName(String username){
		Query q = em.createQuery("SELECT a FROM Reader a WHERE a.username=:name").setParameter("name", username);
		return (Reader)q.getSingleResult();
	}
	

}
