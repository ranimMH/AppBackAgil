package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dao.AppRoleRepository;
import com.example.demo.dao.AppUserRepository;
import com.example.demo.entities.AppUser;
import com.example.demo.services.AcountService;

import lombok.Data;

@RestController
public class UserController {
	@Autowired
	AcountService acountService;
	@Autowired
	AppUserRepository userRepository;
	@Autowired
	AppRoleRepository roleRepository;

	@PostMapping("/register")
	public AppUser register(@RequestBody UserForm userForm) {
		return acountService.saveUser(userForm.getUsername(), userForm.getPassword(), userForm.getConfirmedPassword());
	}

//	@PostMapping("/login")
//	public AppUser login(@RequestBody UserForm userForm) {
//		return acountService.loadUserByUserName(userForm.getUsername());
//	}
//	@PostMapping(value = {"/auth"}, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) {
//	    Authentication authentication = authenticationService.authenticate(authenticationRequest);
//
//	    if(authentication != null && authentication.isAuthenticated()) {
//	        JwtTokens tokens = jwtTokenService.createTokens(authentication);
//	        return ResponseEntity.ok().body(tokens);
//	    }
//
//	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
//	}

}

@Data
class UserForm {
	private String username;
	private String password;
	private String confirmedPassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}
}
