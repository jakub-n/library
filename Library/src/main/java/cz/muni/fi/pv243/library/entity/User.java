package cz.muni.fi.pv243.library.entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.codec.binary.Base64;

@Entity
@Table(name="User")
public class User {

    public static final int MINIMUM_PASSWORD_LENGTH = 5;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false)
    @JoinTable(name = "UserRoles", joinColumns = @JoinColumn(name = "User_username"))
    private Set<Role> roles;

    @Id
    private String username;


    @NotNull
    private String password;


    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
		try {
	    	MessageDigest m;
			m = MessageDigest.getInstance("SHA-256");
	        m.update(password.getBytes(), 0, password.length());
	        this.password = Base64.encodeBase64String(m.digest());;
		} catch (NoSuchAlgorithmException e) {
	        //TODO log
		}
    }

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	
    
}
