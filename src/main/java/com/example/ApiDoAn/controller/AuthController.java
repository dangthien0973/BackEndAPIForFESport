package com.example.ApiDoAn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.ApiDoAn.comom.ERole;
import com.example.ApiDoAn.entity.PermissionEntity;
import com.example.ApiDoAn.entity.ProductEntity;
import com.example.ApiDoAn.entity.RefreshTokenEntity;
import com.example.ApiDoAn.entity.RoleEntity;
import com.example.ApiDoAn.entity.UserEntity;
import com.example.ApiDoAn.impl.UserServiceImp;
import com.example.ApiDoAn.reponse.CountUserByMonth;
import com.example.ApiDoAn.reponse.JwtResponse;
import com.example.ApiDoAn.reponse.MessageReponse;
import com.example.ApiDoAn.reponse.ResponseObject;
import com.example.ApiDoAn.repository.Permissionreponsitory;
import com.example.ApiDoAn.repository.RoleRepository;
import com.example.ApiDoAn.repository.UserRepository;
import com.example.ApiDoAn.request.EmailReq;
import com.example.ApiDoAn.request.LoginReq;
import com.example.ApiDoAn.request.RegisterEmail;
import com.example.ApiDoAn.request.RegisterReq;
import com.example.ApiDoAn.request.ResetPasswordRequest;
import com.example.ApiDoAn.request.TokenRefreshReq;
import com.example.ApiDoAn.request.VerifyCodeReq;

import com.example.ApiDoAn.security.RefreshTokenService;
import com.example.ApiDoAn.security.jwt.JwtUtils;
import com.example.ApiDoAn.security.services.UserDetailsImpl;
import com.example.ApiDoAn.service.IUserService;


import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	RefreshTokenService refreshTokenService;
	@Autowired
	UserServiceImp iUserService;
	  @Autowired
	    UserRepository userRepository;

	    @Autowired
	    RoleRepository roleRepository;

	    @Autowired
	    PasswordEncoder encoder;
		@Autowired
		Permissionreponsitory per;
	@PostMapping(value = "/registerEmail")
	public ResponseEntity<?> registerEmail(@Valid @RequestBody RegisterEmail registerEmail)
			throws MessagingException, IOException {
		String result = this.iUserService.registerEmail(registerEmail);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), result, ""));
	}

	@PostMapping(value = "/verifyEmail")
	public ResponseEntity<?> VerifyEmail(@Valid @RequestBody VerifyCodeReq verifyCode) {
		boolean isCheckVerify = iUserService.verify(verifyCode.getVerifyCodeEmail());
		if (isCheckVerify)
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject(HttpStatus.OK.value(), "Verification successful, you can now login", ""));
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.NOT_FOUND.value(),
				"Verification failed,you need to check the verifyCode in the Email or verifyCode expire", ""));
	}

	@PostMapping("/refreshVerifyCode")
	public ResponseEntity<?> refreshVerifyCode(@Valid @RequestBody EmailReq refreshVerifyCodeReq) {
		boolean existEmail = iUserService.refeshVerifyCode(refreshVerifyCodeReq.getEmail());
		if (existEmail)
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(),
					"please check your email for verification instructions", ""));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ResponseObject(HttpStatus.NOT_FOUND.value(), "This email does not exist in the database", ""));
	}

	@PostMapping(value = "/forgot-password")
	public ResponseEntity<?> forgotPassword(@Valid @RequestBody EmailReq forgotReq) {
		boolean isCheckforgot = iUserService.checkForgot(forgotReq.getEmail());
		if (isCheckforgot)
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(),
					"please check your email for verification instructions", ""));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(HttpStatus.NOT_FOUND.value(),
				"Verification failed,the email you entered is wrong", ""));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
		boolean resetPassword = iUserService.ResetPassword(resetPasswordRequest);
		if (resetPassword)
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject(HttpStatus.OK.value(), "reset Password successful", ""));
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "reset Password fail", ""));

	}

//    @PostMapping("/refreshtoken")
//    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshReq request) {
//        String requestRefreshToken = request.getRefreshToken();
//
//        return refreshTokenService.findByToken(requestRefreshToken)
//                .map(refreshTokenService::verifyExpiration)
//                .map(RefreshTokenEntity::getUserEntity)
//                .map(user -> {
//                    String token = jwtUtils.generateTokenFromUsername(user.getUserName());
//                    return ResponseEntity.ok(new TokenRefreshResponse(HttpStatus.OK.value(), token, requestRefreshToken));
//                })
//                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
//                        "Refresh token is not in database!"));
//    }

	@GetMapping("/logout")
	public ResponseEntity<?> logoutUser(@RequestParam(value = "userName") String userName) {
		boolean isCheckLogOut = iUserService.checklogout(userName);
		if (isCheckLogOut) {
			return ResponseEntity.ok(new ResponseObject(HttpStatus.OK.value(), "Log out successful!", ""));
		}
		return ResponseEntity.ok(new ResponseObject(HttpStatus.BAD_REQUEST.value(), "Log out fail!", ""));
	}

	@GetMapping("/checkUserName")
	public ResponseEntity<?> checkUser(@RequestParam String username) {
		boolean check = this.iUserService.finByUserName(username);
		if (check)
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject(HttpStatus.NOT_FOUND.value(), "exit user!", ""));

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.value(), "successful!", ""));

	}
	
	// làm lại theo hướng để hiểu
	@PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterReq signUpRequest) {
		Date dt=new Date();
        if (userRepository.existsByUserName(signUpRequest.getUserName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageReponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageReponse("Error: Email is already in use!"));
        }
        // tạo contrustor để lưu mã hóa thông tin 
        UserEntity user = new UserEntity(signUpRequest.getUserName(), signUpRequest.getEmail(),
                            encoder.encode(signUpRequest.getPassword()),signUpRequest.getImageBase64(),true,signUpRequest.getCustomerName(),signUpRequest.getPhone());
        user.setPasswords(encoder.encode(signUpRequest.getPassword()));
        user.setDateCreated(dt);
        Set<String> asignRoles = signUpRequest.getRole();
        Set<RoleEntity> roles = new HashSet();
      
        // Nếu không truyền thì set role mặc định là ROLE_USER
        if (asignRoles == null) {
            RoleEntity userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } 
        else {
            asignRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        RoleEntity adminRole = roleRepository.findByName("ROLE_ADMIN")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        RoleEntity modRole = roleRepository.findByName("ROLE_MODERATOR")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        RoleEntity userRole = roleRepository.findByName("ROLE_USER")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageReponse("User registered successfully!"));
    }

	  @PostMapping("/login")
	  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginReq loginRequest) {
        
	    Authentication authentication = authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    String jwt = jwtUtils.generateJwtToken(authentication);
	    
	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
	    List<String> roles = userDetails.getAuthorities().stream()
	        .map(item -> item.getAuthority())
	        .collect(Collectors.toList());
	    return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(HttpStatus.OK.value(),jwt, userDetails.getImageBase64(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles,userDetails.getImageBase64()));
	  }
		@PostMapping("getAllUser")
		public ResponseEntity<?> getAllUser(@RequestParam(value = "pageIndex") int pageIndex) {
			int pageIndextoCheck =0;
			pageIndextoCheck =pageIndex ;
			int pageSize = 10;
			Pageable pageable = PageRequest.of(pageIndex, pageSize);
			Page<UserEntity> result = userRepository.findAll(pageable);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject(HttpStatus.OK.value(), "successfully!", result));
		}
		@PostMapping("DeleteUser")
		public ResponseEntity<?> Detele(@RequestParam(value = "id") long id) {
			int pageIndextoCheck =0;
			 userRepository.deleteById(id);
			
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject(HttpStatus.OK.value(), "successfully!","Xóa thành công"));
		}
		@PostMapping("/UpdateUser")
	    public ResponseEntity<?> UpdateUser( @RequestBody RegisterReq signUpRequest) {
	        // tạo contrustor để lưu mã hóa thông tin 
			Date dt=new Date();
	        UserEntity user = new UserEntity(signUpRequest.getUserName(), signUpRequest.getEmail(),
	                            signUpRequest.getPassword(),signUpRequest.getImageBase64(),true,signUpRequest.getCustomerName(),signUpRequest.getPhone());
	        user.setId(signUpRequest.getId());
	       System.err.println(signUpRequest.getPassword());
	        user.setPasswords(signUpRequest.passwords);
	        user.setDateCreated(dt);
	        
	        Set<String> asignRoles = signUpRequest.getRole();
	        Set<RoleEntity> roles = new HashSet();
	            asignRoles.forEach(role -> {
	                switch (role) {
	                    case "admin":
	                        RoleEntity adminRole = roleRepository.findByName("ROLE_ADMIN")
	                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	                        roles.add(adminRole);

	                        break;
	                    case "mod":
	                        RoleEntity modRole = roleRepository.findByName("ROLE_MODERATOR")
	                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	                        roles.add(modRole);

	                        break;
	                    default:
	                        RoleEntity userRole = roleRepository.findByName("ROLE_USER")
	                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	                        roles.add(userRole);
	                }
	            });
	        user.setRoles(roles);
	        userRepository.save(user);

	        return ResponseEntity.ok(new MessageReponse("User registered successfully!"));
	    }
		@PostMapping("GetAllUserToChart")
		public ResponseEntity<?> GetAllUserToChart() {
			List<UserEntity> result = userRepository.findAll();
			ArrayList<CountUserByMonth> resultCount = new ArrayList<CountUserByMonth>();
		    for (UserEntity userEntity : result) {
		    	CountUserByMonth user = new CountUserByMonth();
		    	user.setMonth(userEntity.getDateCreated().getMonth());
		    	user.setYear(userEntity.getDateCreated(). getYear());
		    	resultCount.add(user);
			}
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject(HttpStatus.OK.value(), "successfully!",resultCount));
		}
}
