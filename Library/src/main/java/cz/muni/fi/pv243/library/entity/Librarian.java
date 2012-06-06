package cz.muni.fi.pv243.library.entity;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;


@Entity
@Access(AccessType.FIELD)
public class Librarian extends Employee implements Serializable {

	private static final long serialVersionUID = 1L;


}
