package com.flightbooking.serviceimpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightbooking.exception.UserNotFoundException;
import com.flightbooking.model.User;
import com.flightbooking.repository.UserRepository;
import com.flightbooking.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repository;
	
	public List<User> getAllUsers() {
		return repository.findAll();
	}

	//registering the user details 
	public User registerUser(User user) {		
		return repository.save(user);
	}


	//deleting the user account, if account does not exists throws exception
	public void deleteUser(long userId) throws UserNotFoundException{
		
		if(repository.existsById(userId)) {
			User usr= repository.getOne(userId);
			repository.delete(usr);
			
		}
		else {
			throw new UserNotFoundException();
		}
	}

	//getting the details of user by id, if it's not there throws exception
	public User getUserById(long userId) throws UserNotFoundException {
		User usr;
		if(repository.findById(userId).isEmpty()) {
			throw new UserNotFoundException();
		}
		else {
			usr=repository.findById(userId).get();
		}
		return usr;
	}


	//check the login by validating both the email id and password
	public String loginUser(User user) throws UserNotFoundException {
		if(repository.validateUser(user.getEmailId(),user.getPassword()).isEmpty())
			{
				throw new UserNotFoundException("Invalid User");
			}
		return "Login Successful";
	}

	//updating the mobile number and the address of the user.
	public User updateUser(User user, long userId) throws UserNotFoundException {
		
		if(repository.findById(userId).get()==null) {
			User u =repository.findById(userId).get();
			u.setAddress(user.getAddress());
			u.setMobile(user.getMobile());
			return this.repository.save(u);
			
		}		
		throw new UserNotFoundException();
	}

	
	
}
	
