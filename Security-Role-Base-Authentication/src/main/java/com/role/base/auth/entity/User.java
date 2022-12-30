package com.role.base.auth.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;

@Data
@Entity
public class User {

	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String userName;
	private String password;
	private boolean enabled;
	@Column(name = "failed_attemp")
	private Integer failedAttemp;
	@Column(name="account_non_locked")
	private boolean accountNonLock;
	@Column(name="lock_time")
	private Date lockTime;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name="users_id"),
			inverseJoinColumns = @JoinColumn(name="roles_id")
			)
	private Set<Role> role = new HashSet<>();

}
