package pl.monitor.working_time_control.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.monitor.working_time_control.Repository.ProjectRepository;
import pl.monitor.working_time_control.Repository.UserRepository;
import pl.monitor.working_time_control.controller.form.DeleteProjectForm;
import pl.monitor.working_time_control.controller.form.ProjectAndUser;
import pl.monitor.working_time_control.entity.Project;
import pl.monitor.working_time_control.entity.User;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/project")
public class ProjectController {
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/save")
    public ModelAndView createNewProject() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("project_creat_new_project_page");
        modelAndView.getModel().put("project", new Project());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView createNewProject(@ModelAttribute("project") Project project) {
        projectRepository.save(project);
        Map<String, Object> projects = new HashMap<>();
        projects.put("created", project);
        return new ModelAndView("project_after_create_new_project_page", projects);
    }

    @GetMapping("/print")
    public ModelAndView printAll() {
        ModelAndView modelAndView = new ModelAndView();
        Iterable<Project> projects = projectRepository.findAll();
        modelAndView.setViewName("project_mainMenu_printAll_page");
        modelAndView.getModel().put("projects", projects);
        List<ProjectAndUser> projectAndUserList = new ArrayList<>();

        for (Project project : projectRepository.findAll()) {
            List<User> users = userRepository.findUsers(project.getProjectID());
            ProjectAndUser projectAndUser = new ProjectAndUser(project, users);
            projectAndUserList.add(projectAndUser);
        }
        modelAndView.getModel().put("userList", projectAndUserList);
        return modelAndView;
    }

    @GetMapping("/delete")
    public ModelAndView deleteProject() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("project_delate_page");
        modelAndView.getModel().put("projects", projectRepository.findAll());
        modelAndView.getModel().put("selectedProject", new DeleteProjectForm());
        return modelAndView;
    }


    @PostMapping("/delete")
    public String deleteProject(@ModelAttribute("project") DeleteProjectForm project) {
        ModelAndView modelAndView = new ModelAndView();
        projectRepository.deleteById(project.getId());
        modelAndView.setViewName("project_mainMenu_printAll_page");
        return "redirect:/project/print";
    }


}

