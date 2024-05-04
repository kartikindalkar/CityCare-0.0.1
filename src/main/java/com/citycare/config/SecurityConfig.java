package com.citycare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService getDetailsService() {
		return new UserDetailsService();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		/*
		 * http.csrf().disable().authorizeHttpRequests()
		 * .requestMatchers("/assets/**").permitAll()
		 * .requestMatchers("/images/**").permitAll()
		 * .requestMatchers("/","/signup","/login","/saveUser").permitAll()
		 * .anyRequest().authenticated()
		 * .and().formLogin().loginPage("/login").loginProcessingUrl("/userLogin")
		 * .defaultSuccessUrl("/account-home",true).permitAll();
		 */
        http.csrf(csrf -> {
			try {
				csrf.disable().authorizeHttpRequests()
				        .requestMatchers("/assets/**").permitAll()
				        .requestMatchers("/images/**").permitAll()
				        .requestMatchers("/index","/", "/signup", "/login", "/saveUser").permitAll()
				        .anyRequest().authenticated();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).formLogin(login -> login.loginPage("/login").loginProcessingUrl("/userLogin")
                .defaultSuccessUrl("/account-home", true).permitAll());
		 

		return http.build();

	}
}
