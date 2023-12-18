package com.arqui.integrador.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@CreationTimestamp
	@Column(name = "date_of_sign", nullable = false, updatable = false)
	private LocalDate dateOfSign;
	
	private double amount;
	
	@Column(name = "mp_account", nullable = false, unique = true)
	private int mpAccount;
	
	@Column(name = "is_available", nullable = false)
	private boolean isAvailable;
	
	@ManyToMany(mappedBy = "accounts", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private List<User> users;
	
	public boolean addUser(User user) {
		if(users.contains(user))
			return false;
		return this.users.add(user);
	}
	
	public boolean deleteUser(User user) {
		return this.users.remove(user);
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", dateOfSign=" + dateOfSign + ", amount=" + amount + ", mpAccount=" + mpAccount
				+ ", isAvailable=" + isAvailable + "]";
	}
}
