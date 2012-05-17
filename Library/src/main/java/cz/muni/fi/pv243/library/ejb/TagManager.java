package cz.muni.fi.pv243.library.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.pv243.library.entity.Tag;

@Stateless
public class TagManager {
	
	@PersistenceContext
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

}