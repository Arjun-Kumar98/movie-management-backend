package com.cinematheater.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;
import com.cinematheater.model.*;
import com.cinematheater.service.*;
import com.cinematheater.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.*;
import java.util.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordService passwordService;

  @Validated
  @CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/registerUser")

	public ResponseEntity<String> registerUser(@RequestBody UserEntity userDetails){
	  logger.info("the user email is === {}",userDetails.getEmailId());
	  userDetails.setPassword(passwordService.hashPassword(userDetails.getPassword()));
		String responsemsg = userService.saveUserDetails(userDetails);
		return ResponseEntity.ok(responsemsg);
	}
  
  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/loginUser")
  public ResponseEntity<LoginResponseDTO> userLogin(@RequestParam("emailid") @NotBlank(message = "email id cannot be blank") String username,
			@RequestParam("password") @NotBlank(message="password cannot be blank") String password){

	    LoginResponseDTO loginResponse = userService.loginUser(username,password);
	    return ResponseEntity.ok(loginResponse);
  }
  
  
  
}
