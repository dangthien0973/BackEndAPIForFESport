package com.example.ApiDoAn.entity;

import lombok.*;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "userName"),
        })
public class UserEntity extends BaseEntity {

 
//    @Length(min = 5,max = 20, message = "*Your password must have at least 5 characters")
    @Column
    private String userName;
    @Column
 
    private String email;
    @Column

    private String passwords;
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(	name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    @Column
    private String address;
    @Column
    private String phone;
    @Column
    private String gender;
    @Column
    private boolean enabled;
    @Column
    private int verificationCode;
    @Column
    private int verifiForgot;
    @Column
    private String statuss;
    @Column
    private String customerName;
    @Column
    private String imageBase64;

    @OneToOne(mappedBy = "userEntity")
    private RefreshTokenEntity refreshToken;
    public UserEntity(String username, String email, String password, String imageBase64, boolean enabled,String customerName,String phone ) {
        this.userName = username;
        this.email = email;
        this.imageBase64 = imageBase64;
        this.customerName = customerName;
        this.phone = phone;
        
    }
    public UserEntity(Long id,String username, String email, String password, String imageBase64, boolean enabled,String customerName,String phone ) {
        this.userName = username;
        this.email = email;
        this.imageBase64 = imageBase64;
        this.customerName = customerName;
        this.phone = phone;
     
        
    }
    public UserEntity(String username,String email) {
        this.userName = username;
        this.email = email;

    }
    public UserEntity() {
      
    }
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswords() {
		return passwords;
	}

	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}

	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(int verificationCode) {
		this.verificationCode = verificationCode;
	}

	public int getVerifiForgot() {
		return verifiForgot;
	}

	public void setVerifiForgot(int verifiForgot) {
		this.verifiForgot = verifiForgot;
	}

	public String getStatuss() {
		return statuss;
	}

	public void setStatuss(String statuss) {
		this.statuss = statuss;
	}

	public RefreshTokenEntity getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(RefreshTokenEntity refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getImageBase64() {
		return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}
    

}
