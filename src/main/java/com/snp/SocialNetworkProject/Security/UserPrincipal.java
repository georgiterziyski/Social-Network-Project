package com.snp.SocialNetworkProject.Security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.snp.SocialNetworkProject.models.Role;
import com.snp.SocialNetworkProject.models.User;

public class UserPrincipal implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;
	private Collection<GrantedAuthority> authorities = new ArrayList<>();
	
	public UserPrincipal(User user) {
		this.user = user;
		final Set<Role> roles = user.getRoles();
		if (null != roles && !roles.isEmpty()) {
			roles.forEach(role -> 
			authorities.add(new SimpleGrantedAuthority(role.getCode())));
		} else {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		this.user.getPermissionsList().forEach(p -> {
			GrantedAuthority authority = new SimpleGrantedAuthority(p);
			authorities.add(authority);
		});
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.user.isActive() == 1;
	}
	
}
