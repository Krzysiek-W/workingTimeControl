package pl.monitor.working_time_control.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.monitor.working_time_control.entity.Project;
import pl.monitor.working_time_control.entity.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByProjectProjectID(Integer id);
    Project findByTaskID(Long id);
}
