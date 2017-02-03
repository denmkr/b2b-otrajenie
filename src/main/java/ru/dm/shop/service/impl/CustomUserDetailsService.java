package ru.dm.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dm.shop.domain.UserDetail;
import ru.dm.shop.entity.User;
import ru.dm.shop.repository.UserRepository;
import ru.dm.shop.repository.UserRoleRepository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Resource
	public UserRepository userRepository;
	@Resource
	public UserRoleRepository userRoleRepository;
	
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		return this.getUser(username);
	}

	public UserDetail getUser(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(username + " not found");
		}

		UserDetail userDetail = new UserDetail(user.getUsername(), user.getPassword(), userRoleRepository.findByUserId(user.getId()).getAuthority());

		return userDetail;
	}

}

