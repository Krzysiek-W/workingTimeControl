package pl.monitor.working_time_control.entity;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_task")
    private Long taskID;

    @Column(name = "task_name")
    private String taskName;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime timeForTask;

    @Column(name = "task_status")
    private String taskStatus;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "description_task")
    private String descriptionTask;

    @ManyToOne
    private Project project;
    @ManyToOne
    private User user;
}


