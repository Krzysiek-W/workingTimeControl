package pl.monitor.working_time_control.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.monitor.working_time_control.Repository.UserRepository;
import pl.monitor.working_time_control.entity.Privilage;
import pl.monitor.working_time_control.entity.User;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void save(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setPrivilage(Privilage.USER);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> maybeUser = userRepository.findByMail(username);
        if(maybeUser.isPresent()) {
            return maybeUser.get();
        } else {
            throw new UsernameNotFoundException("User not found!");
        }
    }
}
