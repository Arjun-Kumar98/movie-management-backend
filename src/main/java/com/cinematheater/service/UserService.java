package com.cinematheater.service;

import com.cinematheater.jwt.*;
import java.util.Optional;
import java.util.stream.Collectors;
import com.cinematheater.controller.UserController;
import java.util.*;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import com.cinematheater.dto.*;
import org.slf4j.LoggerFactory;
import com.cinematheater.exception.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cinematheater.repository.*;
import com.cinematheater.model.*;
import com.cinematheater.service.PasswordService;

@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordService passwordService;
	
	@Autowired
    private JwtUtil jwtTokenProvider;

	
	public String saveUserDetails(UserEntity userDetails) {
		logger.info("The emaildis {} and password");
		userRepository.save(userDetails);
		return "The user details have been saved successfully";
	}
	
	public LoginResponseDTO loginUser(String emailId,String password) {
		Optional<UserEntity> optuser = userRepository.findByEmailId(emailId);

		if(optuser.isPresent() && passwordService.verifyPassword(password,optuser.get().getPassword())) {
			String token = jwtTokenProvider.generateToken(optuser.get().getEmailId());
			LoginResponseDTO loginResponse = new LoginResponseDTO("success",token,optuser.get().getUserId());
			return loginResponse;
		}else {
			return new LoginResponseDTO("incorrect username or password","fail",0);
		}
	}
}
