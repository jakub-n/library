package cz.muni.fi.pv243.library.entity;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Access(AccessType.FIELD)
public class Employee extends User implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 2, max = 50, message = "Křestní jméno musí být v rozsahu 2-50 znaků.")
	private String firstName;

	@NotNull
	@Size(min = 2, max = 100, message = "Příjmení musí být v rozsahu 2-100 znaků.")
	private String lastName;

	private boolean active = true;

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
