package com.cd.belt3.models;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.cd.belt3.models.User;
import com.cd.belt3.models.Role;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue
	private Long id;
	
	@Size(min=3)
	private String name;
	
	@Email
	private String email;
	
	@Size(min=5)
	private String password;
	@Transient
	private String passwordConfirm;
	
	@Size(min=1)
	private String description;
	
	private Date createdAt;
	private Date updatedAt;
	
	@PrePersist
	protected void onCreate(){
	this.createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate(){
	this.updatedAt = new Date();
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id"))
	private List<Role> roles;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
			name = "friends",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "friend_id"))
	private List<User> added_friends;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
			name = "requested",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "request_id"))
	private List<User> requested_friend;
	
	public User() {
	}
	
	public User(String name, String email, String password, String description) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<User> getAdded_friends() {
		return added_friends;
	}

	public void setAdded_friends(List<User> added_friends) {
		this.added_friends = added_friends;
	}

	public List<User> getRequested_friend() {
		return requested_friend;
	}

	public void setRequested_friend(List<User> requested_friend) {
		this.requested_friend = requested_friend;
	}

}
