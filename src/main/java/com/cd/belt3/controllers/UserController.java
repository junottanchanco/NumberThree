package com.cd.belt3.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import com.cd.loginRegister.models.Role;
import com.cd.belt3.models.User;
import com.cd.belt3.services.UserService;
import com.cd.belt3.validator.UserValidator;

@Controller
public class UserController{
	private UserService userService;
	private UserValidator userValidator;
	
	public UserController(UserService userService, UserValidator userValidator) {
		this.userService = userService;
		this.userValidator = userValidator;
	}
	
	@RequestMapping("/login")
	public String login(@Valid @ModelAttribute("user") User user, @RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
		if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successfull!");
        }
		return "login.jsp";
	}
	
	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
		userValidator.validate(user, result);

        if (result.hasErrors()) {
            return "login.jsp";
        }
        
        userService.saveUserWithAdminRole(user);
        return "redirect:/login";
    }
	
	@RequestMapping(value = {"/", "/home"})
	public String home(Principal principal, Model model) {
		
		String email = principal.getName();
		User user = userService.findByEmail(email);
		userService.updateUserDate(user.getId(), user);
		model.addAttribute("currentUser",  user);
		model.addAttribute("users", userService.findAll());
		model.addAttribute("added", user.getAdded_friends());
		model.addAttribute("requested", user.getRequested_friend());
		return "profilePage.jsp";
	}
	
	@RequestMapping("/dash")
	public String dash(Principal principal, Model model) {
		String email = principal.getName();
		User user = userService.findByEmail(email);
//		userService.updateUserDate(user.getId(), user);
		model.addAttribute("currentUser",  user);
		model.addAttribute("friends", userService.findAll());
		model.addAttribute("requested", user.getRequested_friend());
//		List<User> allUsers = userService.findAll();
//		List<User> requested = user.getRequested_friend();
//		
//		System.out.println(allUsers);
//		System.out.println(requested);
		
//		for(int i = 0; i < allUsers.size(); i++) {
//			for(int j = 0; j < requested.size(); j++) {
//				if(allUsers.get(i).getId().equals(requested.get(j).getId())) {
//					allUsers.remove(j);
//					System.out.println(allUsers.get(i).getName());
//				}
//			}
//		}
//		model.addAttribute("friends", allUsers);
		return "dashboard.jsp";
	}
	
	@RequestMapping("/connect/{user_id}/{friend_id}")
	public String connect(@PathVariable("user_id") Long user_id, @PathVariable("friend_id") Long friend_id) {
		User user = userService.findUserById(friend_id);
		User friend = userService.findUserById(user_id);
		
		List<User> requested = user.getRequested_friend();
		
		for(int i = 0; i < requested.size(); i++) {
			if(requested.get(i).getId() == friend.getId()) {
				return "redirect:/";
			}
		}
		requested.add(friend);
		user.setRequested_friend(requested);
		userService.updateUser(user);
		return "redirect:/dash";
	}
	
	@RequestMapping("/add/{user_id}/{friend_id}")
	public String accept(@PathVariable("user_id") Long user_id, @PathVariable("friend_id") Long friend_id) {
		User user = userService.findUserById(user_id);
		User friend = userService.findUserById(friend_id);
		
		List<User> network = user.getAdded_friends();
		
		for(int i = 0; i < network.size(); i++) {
			if(network.get(i).getId() == friend.getId()) {
				return "redirect:/";
			}
		}
		network.add(friend);
		user.setAdded_friends(network);
		userService.updateUser(user);
		return "redirect:/";
	}
	
	@RequestMapping("/info/{id}")
	public String infoPage(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", userService.findUserById(id));
		return "infoPage.jsp";
	}
	
	@RequestMapping("/ignore/{user_id}/{friend_id}")
	public String deleteUser(@PathVariable("user_id") Long user_id, @PathVariable("friend_id") Long friend_id) {
		User user = userService.findUserById(user_id);
		User friend = userService.findUserById(friend_id);
		
		user.getRequested_friend().remove(friend);
		userService.updateUser(user);
		return "redirect:/";
	}
	
	@RequestMapping("/makeAdmin/{id}")
	public String makeAdmin(@PathVariable("id") Long id) {
		User user = userService.findUserById(id);
		userService.updateUserWithAdminRole(user);
		return "redirect:/admin";
	}
	
	@RequestMapping("/makeUser/{id}")
	public String makeUser(@PathVariable("id") Long id) {
		User user = userService.findUserById(id);
		userService.updateUserWithUserRole(user);
		return "redirect:/admin";
	}
}
