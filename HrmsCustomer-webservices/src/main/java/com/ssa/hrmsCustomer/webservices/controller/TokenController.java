package com.ssa.hrmsCustomer.webservices.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ssa.hrmsCustomer.common.MessageReader;
import com.ssa.hrmsCustomer.common.exception.ApplicationException;
import com.ssa.hrmsCustomer.common.exception.BadCredentialException;
import com.ssa.hrmsCustomer.common.exception.UsernotactiveException;
import com.ssa.hrmsCustomer.dao.UserRepository;
import com.ssa.hrmsCustomer.dto.model.JwtRequest;
import com.ssa.hrmsCustomer.dto.model.JwtResponse;
import com.ssa.hrmsCustomer.dto.mysqlentities.User;
import com.ssa.hrmsCustomer.security.config.JwtTokenUtil;
import com.ssa.hrmsCustomer.security.config.JwtUserDetailsService;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;
import com.ssa.hrmsCustomer.security.util.Encryptor;

@RestController
@RequestMapping(value="/webapi")
public class TokenController {
	
	private static final Logger logger = LoggerFactory.getLogger(TokenController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private Encryptor encryptor;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public JwtResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		try{
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		    final LoggedInUser userDetails =  (LoggedInUser) userDetailsService
				                                     .loadUserByUsername(authenticationRequest.getUsername()+":"+authenticationRequest.getSource());
		    if(userDetails.getIsActive() == 0){
		    	throw new UsernotactiveException("user.credential.notActive");
		    }
		    String token = jwtTokenUtil.generateToken(userDetails);
		    String message = MessageReader.getProperty("user.credential.active");
	    	JwtResponse response=new JwtResponse(token,"Bearer", userDetails.getRole().get(0).getRoleTypeId(), message);
	    	logger.info("{} logged in",userDetails.getUsername());
	    	return response; 
			}
        catch(BadCredentialsException ex){
        	  logger.error("While generation token error {}",ex.getMessage());
    	      throw new BadCredentialException("user.credential.nonExist");
	    	   
	       }
		
	}
	

}
