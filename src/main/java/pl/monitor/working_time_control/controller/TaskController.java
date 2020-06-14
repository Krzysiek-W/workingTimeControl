package pl.monitor.working_time_control.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.monitor.working_time_control.Repository.ProjectRepository;
import pl.monitor.working_time_control.Repository.TaskRepository;
import pl.monitor.working_time_control.Repository.UserRepository;
import pl.monitor.working_time_control.controller.form.DeleteTaskForm;
import pl.monitor.working_time_control.entity.Project;
import pl.monitor.working_time_control.entity.Task;
import pl.monitor.working_time_control.entity.User;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
@Slf4j
@RequestMapping("/task")
public class TaskController {
    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/save")
    public ModelAndView createNewTask() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("task_create_new_task_page");
        modelAndView.getModel().put("task", new Task());
        modelAndView.getModel().put("projects", projectRepository.findAll());
        modelAndView.getModel().put("selectedProject", new Project());
        modelAndView.getModel().put("users", userRepository.findAll());
        modelAndView.getModel().put("selectedUser", new User());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView createNewTask(@ModelAttribute("task") Task task) {
        task.setTaskStatus("UNKNOWN");
        taskRepository.save(task);
        Map<String, Object> model = new HashMap<>();
        model.put("created", task);
        return new ModelAndView("task_after_create_new_task_page", model);
    }

    @GetMapping("/print/{projectId}")
    public ModelAndView printAllTaskProject(@PathVariable Integer projectId) {
        List<Task> tasks = taskRepository.findByProjectProjectID(projectId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("task_menu_project_task_page");
        modelAndView.getModel().put("tasks", tasks);
        return modelAndView;
    }

    @GetMapping("/delete")
    public ModelAndView deleteTask() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("task_delete_task_page");
        modelAndView.getModel().put("tasks", taskRepository.findAll());
        modelAndView.getModel().put("selectedTask", new DeleteTaskForm());
        return modelAndView;
    }


    @PostMapping("/delete")
    public String deleteTask(@ModelAttribute("task") DeleteTaskForm task) {
        ModelAndView modelAndView = new ModelAndView();
        taskRepository.deleteById(task.getId());
        modelAndView.setViewName("task_menu_project_task_page");
        String page = "redirect:/mainPage" ;
        return page;

    }


    @GetMapping("/update/{idtask}")
    public ModelAndView updateTask(@PathVariable Long idtask) {
        ModelAndView modelAndView = new ModelAndView();
        Task task = taskRepository.getOne(idtask);
        modelAndView.getModel().put("task", task);
        modelAndView.setViewName("task_update_task_page");
        return modelAndView;
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute("task") Task task) {
        taskRepository.save(task);
        return "redirect:/task/print/" + task.getProject().getProjectID();
    }


    @GetMapping("/changeStatus/{idtask}")
    public ModelAndView updateStatusTask(@PathVariable Long idtask) {
        ModelAndView modelAndView = new ModelAndView();
        Task task = taskRepository.getOne(idtask);
        modelAndView.getModel().put("task", task);
        modelAndView.setViewName("changeStatus");
        return modelAndView;
    }

    @PostMapping("/changeStatus")
    public String updateStatusTask(@ModelAttribute("task") Task task) {
        taskRepository.save(task);
        return "redirect:/task/print/" + task.getProject().getProjectID();
    }

    @GetMapping("/timeToDeadline/{idtask}")
    public ModelAndView printAll(@PathVariable Long idtask) {
        ModelAndView modelAndView = new ModelAndView();
        Task task = taskRepository.getOne(idtask);
        LocalDateTime timeNow = LocalDateTime.now();
        Duration timeLeft = Duration.between(timeNow, Optional.ofNullable(task.getTimeForTask()).orElse(timeNow));
        String hms = String.format("%d D  %02d H %02d M %02d S",
                timeLeft.toDays(),
                timeLeft.toHoursPart(),
                timeLeft.toMinutesPart(),
                timeLeft.toSecondsPart());
        if (timeLeft.isNegative() || timeLeft.isZero()) {
            hms = "Time end, do it ASAP!";
        }
        modelAndView.getModel().put("timeLeft", hms);
        modelAndView.getModel().put("task", task);
        modelAndView.setViewName("task_timeLeft_to_end_task");
        return modelAndView;
    }


}
