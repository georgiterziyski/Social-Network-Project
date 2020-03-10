package com.snp.social_network_project.repos;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.snp.social_network_project.models.User;

@Service
public class DBInit implements CommandLineRunner{

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public DBInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public void run(String... args) throws Exception {

		this.userRepository.deleteAll(); 
		User user = new User("asd", passwordEncoder.encode("asd"), "asd@abv.bg", ""); 
		User admin = new User("admin", passwordEncoder.encode("admin"), "admin@abv.bg", "");
		  
		List<User> users = Arrays.asList(user,admin);
		  
		this.userRepository.saveAll(users);
	}
	
}
