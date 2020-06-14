package pl.monitor.working_time_control.controller.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.monitor.working_time_control.entity.Project;
import pl.monitor.working_time_control.entity.User;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectAndUser {
    private Project project;
    private List<User> entityList = new ArrayList<>();

}
