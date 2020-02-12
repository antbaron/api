package fr.epsi.api.user.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.epsi.api.user.Response;
import fr.epsi.api.user.User;
import fr.epsi.api.user.UserService;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("")
	public List<UserDto> findAll() {
		List<UserDto> users = new ArrayList<UserDto>();
		for (User user : userService.findAll()) {
			users.add(user.toUserDto());
		}
		return users;
	}
	
	@GetMapping("{pseudo}")
	public UserDto find(@PathVariable String pseudo) {
		User user = userService.find(pseudo);
		return user.toUserDto();
	}

	@PostMapping("{pseudo}")
	public Response create(@PathVariable String pseudo, @RequestBody Password pass) throws UnsupportedEncodingException {
		userService.save(pseudo, pass.getPassword());
		return new Response();
	}
	
	@PostMapping("{pseudo}/login")
	public Response login(@PathVariable String pseudo, @RequestBody Password pass) throws LoginFailException {
		try {
			userService.login(pseudo, pass.getPassword());
			return new Response();
		}catch(Exception e) {
			throw new LoginFailException();
		}
		
	}

}
