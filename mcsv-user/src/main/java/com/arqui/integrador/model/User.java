package com.arqui.integrador.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_app")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@Column(nullable = false)
	private Long cellphone;
	
	@Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String firstname;
	
	@Column(nullable = false)
	private String surname;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(name = "user_account",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "account_id"))
	private List<Account> accounts;
	
	public boolean addAccount(Account account) {
		if(accounts.contains(account))
			return false;
		return this.accounts.add(account);
	}
	
	public boolean deleteAccount(Account account) {
		return this.accounts.remove(account);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", cellphone=" + cellphone + ", email=" + email + ", firstname="
				+ firstname + ", surname=" + surname + "]";
	}
}
