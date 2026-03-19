package com.rm.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
	name = "user",
	uniqueConstraints = @UniqueConstraint(
		name = "uk_user_email",
		columnNames = "email"
	)
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String uid;
	
	private String name;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	@Column(length = 20)
	private String phoneNumber;
	
	@Column(length = 255)
	private String email;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
		name = "user_roles",
		joinColumns = @JoinColumn(name="user_id")
	)
	@Column(name = "role")
	@Builder.Default
	private List<String> roles=new ArrayList<String>();
	
	public void update(String name,String password,String phoneNumber) {
		this.name=name;
		this.password=password;
		this.name=phoneNumber;
	}
}
