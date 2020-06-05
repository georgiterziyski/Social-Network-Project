package com.snp.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.snp.models.User;
import com.snp.repos.UserRepository;

@Service
public class UserPrincipalDetailsService implements UserDetailsService{

	private UserRepository userRepository;
	
	public UserPrincipalDetailsService(UserRepository userRepository) {
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
