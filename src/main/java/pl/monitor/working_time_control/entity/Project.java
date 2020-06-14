package pl.monitor.working_time_control.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_project")
    private Integer projectID;

    @OneToMany(mappedBy = "project")
    private Collection<Task> taskEntities = new ArrayList<>();

    private String projectName;

}
