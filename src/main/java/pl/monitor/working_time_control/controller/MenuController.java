package pl.monitor.working_time_control.controller;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.monitor.working_time_control.entity.User;

@Controller
public class MenuController {
    @GetMapping("/mainPage")
    public ModelAndView mainPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        return new ModelAndView("mainPage",
                "currentUser",
                currentPrincipalName);
    }
}
