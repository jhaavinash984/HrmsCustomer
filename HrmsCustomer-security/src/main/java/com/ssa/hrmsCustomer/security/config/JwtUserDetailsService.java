package com.ssa.hrmsCustomer.security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ssa.hrmsCustomer.dao.UserRepository;
import com.ssa.hrmsCustomer.dto.mysqlentities.Role;
import com.ssa.hrmsCustomer.dto.mysqlentities.User;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;


@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String information) throws UsernameNotFoundException {
		String[] informations = information.split(":");
		User user = userRepo.findByUsername(informations[0]);
		if (user == null) {
			throw new UsernameNotFoundException("user.credential.nonExist");
		}
		 List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	     for (Role role : user.getRole()) {
	            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
	     }
		LoggedInUser loggedInUser=new LoggedInUser(user.getUsername(), user.getPassword(), user.getId(),
		         user.getFirstName(), user.getLastName(), user.getEmail(), user.getContactNumber(), user.getGender(), user.getIsActive(),user.getRole(),authorities, user.getOrganisationRoleId(), informations.length >=2 ? informations[1] : null);
		return loggedInUser;
	}
	
	public User save(User user) {
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userRepo.save(newUser);
	}
}
