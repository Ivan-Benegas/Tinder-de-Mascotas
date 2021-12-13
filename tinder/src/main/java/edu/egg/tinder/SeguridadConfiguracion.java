package edu.egg.tinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import edu.egg.tinder.servicios.UsuarioServicio;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SeguridadConfiguracion extends WebSecurityConfigurerAdapter {


	    /*@Override
	    protected void configure(HttpSecurity security) throws Exception
	    {
	     //security.httpBasic().disable();
	     //security.formLogin().disable();
	    	security.csrf().disable().authorizeRequests().anyRequest().permitAll();
	    }*/
	
	@Autowired
	public UsuarioServicio usuarioServicio;
	
	@Autowired
	public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception{
		
		auth
			.userDetailsService(usuarioServicio)
			.passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http.headers().frameOptions().sameOrigin().and()
				.authorizeRequests()
						//.antMatchers("/css/*", "/js/*", "/img/*");
						.antMatchers("/*")
						.permitAll()
				.and().formLogin()
						.loginPage("/login")
								.loginProcessingUrl("/logincheck")
								.usernameParameter("username")
								.passwordParameter("password")
								.defaultSuccessUrl("/inicio")
								.permitAll()
						.and().logout()
								.logoutUrl("/logout")
								.logoutSuccessUrl("/login?logout")
								.permitAll().and().csrf().disable();
		
		//http.csrf().disable().authorizeRequests().anyRequest().permitAll();
		
		
	}
	
	
}
