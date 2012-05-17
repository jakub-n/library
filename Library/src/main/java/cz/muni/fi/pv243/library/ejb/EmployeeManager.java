package cz.muni.fi.pv243.library.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.pv243.library.entity.Employee;

@Stateless
public class EmployeeManager {
	
	@PersistenceContext
	private EntityManager em;
	
	public void create(Employee emp){
		em.persist(emp);
	}
	
//	public void delete(Employee emp){
//		if (em.merge(emp).getLoans().isEmpty()) {
//			em.remove(em.merge(emp));
//		} else {
//			System.out.println("Empolyee in relation with bookLoan!");
//		}
//	}
	
	public void delete(Employee emp){
			em.remove(em.merge(emp));
	}
	
	public void update(Employee emp){
		em.merge(emp);
	}

}
