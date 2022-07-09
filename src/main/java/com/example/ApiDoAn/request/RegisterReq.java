package com.example.ApiDoAn.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin
public class RegisterReq {
    @NotBlank
    private String userName;
    @NotBlank
    private String email;
	@NotBlank
    @Size(min = 6, max = 40)
    private String password;
    private String phone;
    private String gender;
    private String imageBase64;
    private String customerName;
    private boolean enabled;
    private Long id;
    
    public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getImageBase64() {
		return imageBase64;
	}
	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	private Set<String> role;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Set<String> getRole() {
		return role;
	}
	public void setRole(Set<String> role) {
		this.role = role;
	}
    public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}