package pl.monitor.working_time_control.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.monitor.working_time_control.entity.Project;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
