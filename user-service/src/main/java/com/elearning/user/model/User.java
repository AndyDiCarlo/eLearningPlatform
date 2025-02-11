package com.elearning.user.model;

import jakarta.persistence.*;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
@Table(name="users")
public class User extends RepresentationModel<User> {

	@Id
	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name="email")
	private String email;

	@Column(name="comment")
	private String comment;
	
	public User withComment(String comment){
		this.setComment(comment);
		return this;
	}
}