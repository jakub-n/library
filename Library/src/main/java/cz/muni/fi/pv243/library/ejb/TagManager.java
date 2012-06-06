package cz.muni.fi.pv243.library.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pv243.library.entity.Reader;
import cz.muni.fi.pv243.library.entity.Tag;
import cz.muni.fi.pv243.library.resource.LibraryDatabase;

@Stateless
public class TagManager {
	

    @Inject
    @LibraryDatabase
	private EntityManager em;
	
	public void create(Tag tag){
		em.persist(tag);
	}
	
	public void delete(Tag tag){
		em.remove(em.merge(tag));
	}
	
	public void update(Tag tag){
		em.merge(tag);
	}
	
	public List<Tag> getAllTags() {
		Query q = em.createQuery("SELECT t FROM Tag t");
		return q.getResultList();
	}

}
