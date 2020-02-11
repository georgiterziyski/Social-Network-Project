package com.snp.SocialNetworkProject.rest;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.snp.SocialNetworkProject.models.User;
import com.snp.SocialNetworkProject.repos.UserRepository;


@RestController
public class UserManagerRest {

	private final UserRepository repository;

	@Autowired
	public UserManagerRest(final UserRepository repository) {
		this.repository = repository;
	}

	@PostMapping(value = "/logout")
	public String logout(final HttpSession session){
		session.removeAttribute("currentUser");
		return "login.html";
	}
	@PostMapping(value = "/login")
	public String login(@RequestParam(name = "email") 	 final String email,
						@RequestParam(name = "password") final String password,
														 final HttpSession session) {
		final User currentUser = repository.findByEmailAndPassword(email, password);
		if (null != currentUser) {
			session.setAttribute("currentUser", currentUser);
		} else {
			return "login.html";
		}
		return "index.html";
	}

	@PostMapping(value = "/updateUser")
	public ResponseEntity<String> updateUser(@RequestParam(name = "email") 	  final String email,
			 								 @RequestParam(name = "username") final String username,
			 								 @RequestParam(name = "password") final String password,
			 								 								  final HttpSession session){
		final User currentUser = (User) session.getAttribute("currentUser");
		if (null == currentUser) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in!!!");
		}
		final User userToUpdate = repository.findByEmailAndPassword(currentUser.getEmail(), currentUser.getPassword());
		if (null == userToUpdate) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No such User!!!");
		}
		userToUpdate.setEmail(email);
		userToUpdate.setUsername(username);
		userToUpdate.setPassword(password);
		repository.saveAndFlush(userToUpdate);
		session.setAttribute("currentUser", userToUpdate);
		return ResponseEntity.ok().body("User successfully updated");		
	}
	
	@PostMapping(value = "/deleteUser")
	public ResponseEntity<String> deleteUser(@RequestParam(name = "email") 	  final String email,
			 								 @RequestParam(name = "password") final String password,
			 								 								  final HttpSession session){
		final User currentUser = (User) session.getAttribute("currentUser");
		if (null == currentUser) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in!!!");
		}
		final User userToDelete = repository.findByEmailAndPassword(currentUser.getEmail(), currentUser.getPassword());
		if (null == userToDelete) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No such User!!!");
		}
		repository.delete(userToDelete);
		session.removeAttribute("currentUser");
		return ResponseEntity.ok().body("User successfully delete");		
	}
	
	@GetMapping("/getCurrentUser")
	public User getCurrentUser(final HttpSession session) {
		final User currentUser = (User) session.getAttribute("currentUser");
		return (User) repository.findByEmailAndPassword(currentUser.getEmail(), currentUser.getPassword());
	}
	
	@GetMapping("/getAllUsers")
	public List<User> getAllUsers(){
		return repository.findAll();
	}

	@PostMapping(value = "/registerUser")
	public User register(@RequestParam(name = "email") 	  final String email,
						 @RequestParam(name = "username") final String username,
						 @RequestParam(name = "password") final String password) {
		final User newUser = new User(username, password, email, "");
		return repository.saveAndFlush(newUser);
	}
}
