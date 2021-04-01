package com.hyuk.costagram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hyuk.costagram.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final OAuth2DetailsService oAuth2DetailsService;

	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	// 모델 : Image, User, Likes, Follow, Tag
	// Auth : 인증 불필요
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.cors().disable();

		http.authorizeRequests()
			.antMatchers("/", "/user/**", "/image/**", "/follow/**", "/comment/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()
			.and()
			.formLogin().loginPage("/auth/loginForm")
			.loginProcessingUrl("/login") // post /login 주소를 디스패쳐가 확인하면 필터가 낚아챔
			.defaultSuccessUrl("/")
			.and()
			.oauth2Login()
			.userInfoEndpoint()
			.userService(oAuth2DetailsService)
			.and()
			.defaultSuccessUrl("/");
	}
	
}
