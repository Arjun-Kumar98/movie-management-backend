package com.cinematheater.model;
import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="user_list",
uniqueConstraints = @UniqueConstraint(columnNames=("email_id")))
public class UserEntity {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
@Column(name="user_id")
private Integer userId;


@Column(name="email_id", nullable=false)
@NotNull(message="emailid cannot be null")
@Pattern(regexp="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message="Invalid email format")
@JsonProperty("emailId")
private String emailId;

@NotNull(message="password cannot be null")
@Column(name="password", nullable = false)
@JsonProperty("password")
private String password;

public Integer getUserId() {
	return userId;
}

public void setUserId(Integer userId) {
	this.userId = userId;
}

public String getEmailId() {
	return emailId;
}

public void setEmailId(String emailId) {
	this.emailId = emailId;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}
}
