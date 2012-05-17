package cz.muni.fi.pv243.library.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.pv243.library.entity.Booking;

@Stateless
public class BookingManager {
	
	@PersistenceContext
	private EntityManager em;
	
	public void create(Booking booking){
		em.persist(booking);
	}
	
	public void delete(Booking booking){
		em.remove(em.merge(booking));
	}
	
	public void update(Booking booking){
		em.merge(booking);
	}

}
