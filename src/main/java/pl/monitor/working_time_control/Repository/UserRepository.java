package pl.monitor.working_time_control.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.monitor.working_time_control.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByMail(String mail);

    @Query("select distinct u FROM User u join u.task t join t.project p where p.projectID=:idProject")
    List<User> findUsers(Integer idProject);
}
