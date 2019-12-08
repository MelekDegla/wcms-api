package com.wecode.service.impl;

import com.wecode.entity.util.ChangePasswordVM;
import com.wecode.repository.RoleRepository;
import com.wecode.repository.UserRepository;
import com.wecode.entity.User;
import com.wecode.entity.dto.UserDto;
import com.wecode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
	}

	private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
			//authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});
		return authorities;
		//return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findOne(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
    public User save(UserDto user) {
	    User newUser = new User();
		if(user.getId() != null)
	    newUser.setId(user.getId());

	    newUser.setUsername(user.getUsername());
	    if (user.getPassword() != null)
	    newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
	    else
	    	newUser.setPassword(findById(newUser.getId()).getPassword());
		newUser.setSalary(user.getSalary());
		newUser.setBirthdate(user.getBirthdate());
		newUser.setEmail(user.getEmail());
		newUser.setAddress(user.getAddress());
		newUser.setCin(user.getCin());
		newUser.setLeaveBalance(user.getLeaveBalance());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setRib(user.getRib());
		newUser.setLinkedin(user.getLinkedin());
		newUser.setFacebook(user.getFacebook());
		newUser.setInstagram(user.getInstagram());
		if( user.getRoles() != null){
            for (String role: user.getRoles()) {
                newUser.getRoles().add(roleRepository.findByName(role));
            }
        }
		else{
            newUser.getRoles().add(roleRepository.findByName("ADMIN" +
                    ""));

        }


		return userRepository.save(newUser);
    }

    public User update(User user){
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public boolean changePassword(ChangePasswordVM vm , String username){
		User user = findOne(username);
		System.out.println("----------------"+ bcryptEncoder.encode(vm.getOldPassword()) + "--------------------" + user.getPassword());
		System.out.println("------" + vm.getOldPassword());

		if (bcryptEncoder.matches(vm.getOldPassword(),user.getPassword())){
			user.setPassword(bcryptEncoder.encode(vm.getNewPassword()));
			userRepository.save(user);
			return true;
		}
		else return false;

	}
}
