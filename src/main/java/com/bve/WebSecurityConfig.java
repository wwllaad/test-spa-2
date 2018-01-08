package com.bve;


import com.bve.web.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/api/**").hasRole("ADMIN")
//				.antMatchers("/api/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.defaultSuccessUrl("/", true)
				.permitAll()
				.and()
				.httpBasic()
				.and()
				.csrf().disable()
//				.cors().and()
				.logout()
				.logoutSuccessUrl("/");
	}
//		@Bean
//		CorsConfigurationSource corsConfigurationSource() {
//			CorsConfiguration configuration = new CorsConfiguration();
//			configuration.setAllowedOrigins(Arrays.asList("https://localhost:3000"));
//			configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//			source.registerCorsConfiguration("/**", configuration);
//			return source;
//		}


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

}