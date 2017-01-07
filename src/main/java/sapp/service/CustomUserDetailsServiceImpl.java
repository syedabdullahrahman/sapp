package sapp.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sapp.model.Role;
import sapp.model.User;
import sapp.repository.UserRepository;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
	
	UserRepository userRepository;
	
	@Autowired
	public CustomUserDetailsServiceImpl(UserRepository userRepository){
		this.userRepository = userRepository;
	}

	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    	User user = userRepository.findByUsername(username);
	    	if(user==null){
	    		throw new UsernameNotFoundException("Username not found");
	    	}
	        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		        for (Role role : user.getRoles()){
		            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		        }
	        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}

}
