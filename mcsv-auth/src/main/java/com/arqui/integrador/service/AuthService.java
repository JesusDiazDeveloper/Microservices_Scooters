package com.arqui.integrador.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.arqui.integrador.dto.UserCreateDto;
import com.arqui.integrador.exception.UserNotFoundException;
import com.arqui.integrador.model.Role;
import com.arqui.integrador.model.UserAuth;
import com.arqui.integrador.repository.IAuthRepository;

@Service
public class AuthService implements UserDetailsService{
	
	private IAuthRepository authRepository;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public AuthService(IAuthRepository authRepository) {
		this.authRepository = authRepository;
		this.bCryptPasswordEncoder =  new BCryptPasswordEncoder();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UserNotFoundException {

		return authRepository.findByUsername(username)
				.map(user -> new User(user.getUsername(), user.getPassword(), getAuthorities(user.getRole())))
				.orElseThrow(() -> new UserNotFoundException("User not found","User not found with username: " + username));
	}
	
	public void register(UserCreateDto user) {
		String bCryptedPassword = encodePassword(user.getPassword());
		List<Role> roles = user.getRoles().stream().map(Role::new).toList();
		
		this.authRepository.save(UserAuth.builder()
				.username(user.getUsername())
				.password(bCryptedPassword)
				.role(roles)
				.build());
	}
	
	private String encodePassword(String password) {
		
		return bCryptPasswordEncoder.encode(password);
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(List<Role> roles){
		List<GrantedAuthority> authorities = new ArrayList<>();
	    for (Role role: roles) {
	        authorities.add(new SimpleGrantedAuthority(role.getRole()));
	    }
	    return authorities;
	}

}
