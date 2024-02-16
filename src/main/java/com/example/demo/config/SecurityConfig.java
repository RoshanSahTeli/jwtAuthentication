package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.entity.UserDetailsServiceImpl;
import com.example.demo.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
	
	private UserDetailsServiceImpl userimpl;
	
	private JwtAuthenticationFilter authFilter;
	
	public SecurityConfig(UserDetailsServiceImpl userimpl, JwtAuthenticationFilter authFilter) {
		super();
		this.userimpl = userimpl;
		this.authFilter = authFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		return http.
				csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(req->req
						.requestMatchers("/login/**","/register/**").permitAll()
						.anyRequest().authenticated())
				.userDetailsService(userimpl)
				.sessionManagement(session->session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}
