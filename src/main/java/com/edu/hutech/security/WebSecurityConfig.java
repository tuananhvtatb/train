package com.edu.hutech.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/change-password").authenticated()
                .antMatchers("/user-details").authenticated()
                .antMatchers("/user-update").authenticated()
                .antMatchers("/class-management/class-details/export").authenticated()
                .antMatchers("/class-management/export").authenticated()
                .antMatchers("/logout").authenticated()
//                .antMatchers("/class-management/**").hasAnyAuthority("ROLE_ADMIN")
//                .antMatchers("/trainee-management/**").hasAnyAuthority("ROLE_TRAINEE", "ROLE_ADMIN")
//                .antMatchers("/trainer-management/**").hasAnyAuthority("ROLE_TRAINER", "ROLE_ADMIN")
                // Đang lỗi 500 thì là lỗi hệ thống r . Hình như ko có dât mà lấy lấy ra phần từ thứ 0 nên out of
                .and()
                .formLogin() // Submit URL of login page.
                .loginPage("/login")//
//                .usernameParameter("username")
//                .passwordParameter("password")
                .defaultSuccessUrl("/admin", true)
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
             //   .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(1296000)
              //  .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/404");

    }
}
