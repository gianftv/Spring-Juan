package com.sopra.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/", "/css/**","/js/**", "/images/**","/listar/**" ).permitAll()
								.antMatchers("/ver/**").hasAnyRole("USER")
								.antMatchers("/uploads/**").hasAnyRole("USER")
								.antMatchers("/form/**").hasAnyRole("ADMIN")
								.antMatchers("/factura/**").hasAnyRole("ADMIN")
								.antMatchers("/eliminar/**").hasAnyRole("ADMIN")//podemos poner una carpeta o recursos (elimnar es un path
								.anyRequest().authenticated()
								.and()
								.formLogin().loginPage("/login")
								.permitAll()
								.and()
								.logout().permitAll();
								
		
	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception{
		
		
		//EN SPRING FMWK 4.0 VERSIONES DE SB INFERIORES A 2 
		//PasswordEncoder encoder= PasswordEncoderFactories.createDelegatingPasswordEncoder();
		//UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		
		
		//SPRING 5
		UserBuilder users =User.withDefaultPasswordEncoder(); 
		//CREAMOS 2 USUSARIOS EN MEMORIA UNO CON CADA ROL DE LOS QUE VAMOS A UTILIZAR PARA PROBAR
		build.inMemoryAuthentication()
		.withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
		.withUser(users.username("juan").password("12345").roles("USER"));
		
		
			
	}
	
	
	
	

}
