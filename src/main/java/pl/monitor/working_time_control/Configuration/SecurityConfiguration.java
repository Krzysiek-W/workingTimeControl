package pl.monitor.working_time_control.Configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import pl.monitor.working_time_control.entity.Privilage;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/mainPage").hasRole(Privilage.USER.name())
                .antMatchers("/project/*").hasRole(Privilage.USER.name())
                .antMatchers("/task/*").hasRole(Privilage.USER.name())
                .and()
                .formLogin()
                .defaultSuccessUrl("/mainPage");
    }
}
