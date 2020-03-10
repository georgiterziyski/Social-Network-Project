package com.snp.social_network_project.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.snp.social_network_project.models.User;
import com.snp.social_network_project.repos.UserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService{

	private UserRepository userRepository;
	
	public AppUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		final User user = userRepository.findByEmail(email);
		if (null == user) {
			throw new UsernameNotFoundException("User with email=''" + email + "'' does not exist!");
		}
		return new UserPrincipal(user);
	}
	
}
