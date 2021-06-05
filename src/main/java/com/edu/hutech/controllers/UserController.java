package com.edu.hutech.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.edu.hutech.entities.Course;
import com.edu.hutech.entities.User;

import com.edu.hutech.repositories.UserRepository;
import com.edu.hutech.services.implementation.CourseServiceImpl;
import com.edu.hutech.services.implementation.TraineeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping()
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Autowired
    private UserRepository userRepository;

    @Autowired
    TraineeServiceImpl traineeService;

    @Autowired
    CourseServiceImpl courseService;

/**
     * Display the login view
     * @return login view
     */

    @GetMapping("/login")
    public String getLogin() {
        return "pages/user-views/login";
    }

    @RequestMapping(value = "/traineeNameAutoComplete")
    @ResponseBody
    public Course traineeNameAutoComplete(){
        return courseService.findById(1);
    }
/**
     * Handle Logout request when user click on logout button
     * @param request
     * @param response
     * @return the Home view*/
//

/*    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login";
    }*/

/**
     * Display the Password-changing form after login
     * @param model
     * @return change-password form


    @GetMapping("/change-password")
    public String updateUserPasswordForm(Model model) {
        ClassAdmin loginAdmin = classAdminRepository.getLoginAccount();
        model.addAttribute("user", loginAdmin);

        return "pages/user-views/change-password";
    }

*
     * Handle the change-password request, send the request to DB
     * @param newPassword is the new Password user want to set
     * @param oldPassword is the old password which used to login before
     * @param attributes
     * @return home view


    @PostMapping("/change-password")
    public String updateUserPassword(@RequestParam("new-password") String newPassword,
                                     @RequestParam("oldPassword") String oldPassword, RedirectAttributes attributes) {
        ClassAdmin loginAdmin = classAdminRepository.getLoginAccount();
        if (!passwordEncoder.matches(oldPassword, loginAdmin.getPassword())) {
            return "redirect:/change-password?error=true";
        }

        // get new Class Admin with new Password
        loginAdmin.setPassword(passwordEncoder.encode(newPassword));

        classAdminRepository.save(loginAdmin);

        attributes.addFlashAttribute("result", "success");

        return "redirect:/";
    }


    @GetMapping("/user-details")
    public String showDetailsUser(Model model) {
        ClassAdmin loginAdmin = classAdminRepository.getLoginAccount();
        model.addAttribute("user", loginAdmin);

        return "pages/user-views/user-details";
    }

    @GetMapping("/user-update")
    public String showUpdateUser(Model model) {
        ClassAdmin loginAdmin = classAdminRepository.getLoginAccount();

        User user = userRepository.getOne(loginAdmin.getId());

        model.addAttribute("user", user);

        return "pages/user-views/user-update";
    }


    @PostMapping("/user-update")
    public String updateUser(@Valid User user, Model model) {

        ClassAdmin loginAdmin = classAdminRepository.getLoginAccount();

        User userUpdate = userRepository.getOne(loginAdmin.getId());
        userUpdate.setName(user.getName());
        userUpdate.setEmail(user.getEmail());
        userUpdate.setFacebook(user.getFacebook());
        userUpdate.setTelNumber(user.getTelNumber());
        userUpdate.setNational(user.getNational());

        userRepository.save(userUpdate);

        model.addAttribute("result", "success");
        model.addAttribute("user", userUpdate);

        return "pages/user-views/user-update";
    }*/


}
