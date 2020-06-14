package pl.monitor.working_time_control.entity;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static pl.monitor.working_time_control.entity.Privilage.PROJECT_MANAGER;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String mail;
    private String password;
    @Enumerated(EnumType.STRING)
    private Privilage privilage;

    @OneToMany(mappedBy = "user")
    private Collection<Task> task;

    public Collection<Task> getTask() {
        return task;
    }

    public void setTask(Collection<Task> task) {
        this.task = task;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority user = new SimpleGrantedAuthority("ROLE_USER");
        if (PROJECT_MANAGER.equals(this.privilage)) {
            SimpleGrantedAuthority admin = new SimpleGrantedAuthority("ROLE_ADMIN");
            return Arrays.asList(admin, user);
        } else {
            return Collections.singletonList(user);
        }
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
