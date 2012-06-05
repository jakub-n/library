package cz.muni.fi.pv243.library.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Access(AccessType.FIELD)
public class Reader extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 2, max = 50, message = "Křestní jméno musí být v rozsahu 2-50 znaků.")
    private String firstName;
    
    @NotNull
    @Size(min = 2, max = 100, message = "Příjmení musí být v rozsahu 2-100 znaků.")
    private String lastName;
    
    @NotNull
    private String street;
    
    @NotNull
    private String city;
    
    @NotNull
    private String zipCode;
    
    @NotNull
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "Musí být zadána validní e-mailová adresa,")
    @Size(min = 3, max = 45, message = "E-mail musí být v rozsahu 3-45 znaků.")
    private String email;
    
    @NotNull
    private String cellNumber;
    private Calendar birthDate;
    private Calendar paidTillDate;
    
    @OneToMany(mappedBy="reader",orphanRemoval=true,fetch = FetchType.LAZY)
    private Set<Booking> bookings;

   

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zip) {
        this.zipCode = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public Calendar getPaidTillDate() {
        return paidTillDate;
    }

    public void setPaidTillDate(Calendar paidTillDate) {
        this.paidTillDate = paidTillDate;
    }

	public Set<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}


}
