package com.example.ApiDoAn.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ApiDoAn.comom.ERole;
import com.example.ApiDoAn.entity.RefreshTokenEntity;
import com.example.ApiDoAn.entity.RoleEntity;
import com.example.ApiDoAn.entity.UserEntity;
import com.example.ApiDoAn.repository.RefreshTokenRepository;
import com.example.ApiDoAn.repository.RoleRepository;
import com.example.ApiDoAn.repository.UserRepository;
import com.example.ApiDoAn.request.RegisterEmail;
import com.example.ApiDoAn.request.RegisterReq;
import com.example.ApiDoAn.request.ResetPasswordRequest;
import com.example.ApiDoAn.security.RefreshTokenService;
import com.example.ApiDoAn.service.IUserService;
import com.example.ApiDoAn.until.SendEmailUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImp implements IUserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private SendEmailUtils sendEmailUtils;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Value("${jtw.app.timeVerifycode}")
    private int timeVerifyCode;


    @Override
    public String registerUser(RegisterReq RegisterReq) throws IOException, MessagingException {
//        if (userRepository.existsByUserName(RegisterReq.getUsername())) {
//            throw new BadRequestException("Error: Username is already taken!");
//        }
//        if (userRepository.existsByEmail(RegisterReq.getEmail())) {
//            throw new BadRequestException("Error: Email is already in use!");
//        }
        // Create new user's account
//        UserEntity user = this.userRepository.findByUserName(RegisterReq.getUserName()).get();
//        user.setPasswords( encoder.encode(RegisterReq.getPassword()));
    	UserEntity user =new UserEntity(RegisterReq.getUserName(),RegisterReq.getEmail(),RegisterReq.getPassword());
    	
        user.setPasswords(RegisterReq.getPassword());
        user.setPhone(RegisterReq.getPhone());
        user.setGender(RegisterReq.getGender());
        Set<String> strRoles = RegisterReq.getRole();
        Set<RoleEntity> roles = new HashSet<>();
        roles =null;
//        addRolesToUser(strRoles, roles);
        user.setRoles(roles);
        userRepository.save(user);
        System.out.println(user.getId());
        refreshTokenService.createRefreshToken(user.getId());
        return "registered successfully";
    }

    @Override
    public String registerEmail(RegisterEmail registerEmail) throws MessagingException, IOException {
        UserEntity user = new UserEntity(registerEmail.getName(), registerEmail.getEmail());
        user.setEnabled(false);
        setVerifyCodeEmail(user);
        userRepository.save(user);
        sendEmailUtils.sendEmailWithAttachment(user, user.getVerificationCode());
        return "registered email successfully, please check your email for verification instructions";
    }

    @Override
    public boolean refeshVerifyCode(String email) {
        UserEntity user = userRepository.findByEmail(email);
        try {
            if (user == null || user.isEnabled()) {
               
            } else {
                if (!checkTimeVerifyCode(user)) {
                    setVerifyCodeEmail(user);
                    sendEmailUtils.sendEmailWithAttachment(user, user.getVerificationCode());
                    userRepository.save(user);
                    return true;
                } else {
                    System.out.println("code chưa hết hiệu lực");
                   
                }
            }


        } catch (NullPointerException | MessagingException | IOException ex) {
            ex.printStackTrace();

        }
        return false;
    }

    @Override
    public boolean verify(int verificationCode) {
        UserEntity user = userRepository.findByVerificationCode(verificationCode);

        try {
            if (user == null || user.isEnabled()) {
//                throw new NotFoundException("verificationCode is incorrect or user is disabled");
                return false;
            } else {
                if (checkTimeVerifyCode(user)) {
                    user.setVerificationCode(0);
                    user.setEnabled(true);
                    userRepository.save(user);
                    return true;
                } else {
//                  throw  new NotFoundException("verificationCode token was expired. Please make a new refeshVerifyCode");
                    return false;
                }

            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();

        }
        return false;
    }

    @Override
    public boolean checkForgot(String email) {
        UserEntity user = userRepository.findByEmail(email);
        try {
            if (user == null || user.isEnabled()) {
                
            } else {
                int code = (int) Math.floor(((Math.random() * 899999) + 100000));
                user.setVerifiForgot(code);
                sendEmailUtils.sendEmailWithAttachment(user, user.getVerifiForgot());
                userRepository.save(user);
                return true;
            }
        } catch (NullPointerException | IOException | MessagingException ex) {

            ex.printStackTrace();

        }
        return false;
    }

    @Override
    public boolean ResetPassword(ResetPasswordRequest resetPasswordRequest) {
        UserEntity user = userRepository.findByVerifiForgot(resetPasswordRequest.getVerifyCodeForgot());
        try {
            if (user == null || user.isEnabled()) {
               
            } else {
                if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())) {
                   
                } else {
                    user.setPasswords(encoder.encode(resetPasswordRequest.getNewPassword()));
                    user.setVerifiForgot(0);
                    userRepository.save(user);
                    return true;
                }
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean checkTimeVerifyCode(UserEntity user) {
        return user.getDateCreated().getTime() + timeVerifyCode - new Date().getTime() >= 0;
    }

    public void addRolesToUser(Set<String> strRoles, Set<RoleEntity> roles) {
        if (strRoles == null) {
            RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        RoleEntity modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
    }

    @Override
    public boolean checklogout(String  userName) {
        UserEntity user = userRepository.findByUserName(userName).get();
        try {
            if (user == null) {
               
            } else {
                user.setVerificationCode(0);
                user.setVerifiForgot(0);
                RefreshTokenEntity refreshToken = refreshTokenService.finByIdUserEntity(user.getId());
                refreshToken.setExpiryDate(null);
                refreshToken.setToken(null);
                refreshTokenRepository.saveAndFlush(refreshToken);
                userRepository.save(user);
                return true;
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        return false;
    }


    public void setVerifyCodeEmail(UserEntity user) {
        int code = (int) Math.floor(((Math.random() * 899999) + 100000));
        user.setVerificationCode(code);
        user.setDateCreated(new Date());
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public boolean finByUserName(String username) {
        return userRepository.existsByUserName(username);
    }

    @Override
    public boolean finByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}


