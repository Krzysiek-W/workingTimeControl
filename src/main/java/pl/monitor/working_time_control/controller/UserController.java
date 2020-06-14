package pl.monitor.working_time_control.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.monitor.working_time_control.entity.User;
import pl.monitor.working_time_control.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public String postSaveUser(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/mainPage";
    }

    @GetMapping("/save")
    public ModelAndView getSaveUser() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user_register_page");
        modelAndView.getModel().put("user", new User());
        return modelAndView;
    }
}
